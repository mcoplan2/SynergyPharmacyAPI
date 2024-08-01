package com.revature.service;

import com.revature.exception.ResourceCouldNotBeCreatedException;
import com.revature.model.Medicine;
import com.revature.model.Payment;
import com.revature.model.Request;
import com.revature.model.User;
import com.revature.model.enums.PayStatus;
import com.revature.model.enums.RequestType;
import com.revature.model.enums.Role;
import com.revature.model.enums.Status;
import com.revature.repository.MedicineRepository;
import com.revature.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final MedicineRepository medicineRepository;
    private final MedicineService medicineService;
    private final UserService userService;
    private final PaymentService paymentService;

    @Autowired
    public RequestService(RequestRepository requestRepository, MedicineRepository medicineRepository, MedicineService medicineService, UserService userService, PaymentService paymentService) {
        this.requestRepository = requestRepository;
        this.medicineRepository = medicineRepository;
        this.medicineService = medicineService;
        this.userService = userService;
        this.paymentService = paymentService;
    }

    public Request createRequest(Request request)  {
        Medicine medicine = medicineService.getMedicineById(request.getMed().getId());
        if(medicine.getStatus() != Status.OUT_OF_STOCK) {
            return requestRepository.save(request);
        }
        throw new RuntimeException(medicine.getName()+" is Out of Stock");
    }

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public Request getRequestById(Integer id) {
        return requestRepository.findById(id).orElseThrow( () -> new RuntimeException("Request could not be found"));
    }

    // PUT
    public Request updateRequest(Request request) {
        Request requestToEdit = requestRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Request could not be found"));
        requestToEdit.setDosageCount(request.getDosageCount());
        requestToEdit.setDosageFreq(request.getDosageFreq());
        requestToEdit.setRequestType(request.getRequestType());
        return requestRepository.save(requestToEdit);
    }

    public void deleteRequest(Request request) {
        Request requestEntered = requestRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Request could not be found"));
        requestRepository.delete(requestEntered);
    }

    public List<Request> getAllByRequestType(RequestType requestType) {
        return requestRepository.getAllByRequestType(requestType);
    }

    public List<Request> getAllByRequestByUser(Integer id) {
        return requestRepository.getAllByCreator_UserId(id);
    }

    public List<Request> getAllRequestByUserAndType(RequestType requestType, Integer id) {
        return  requestRepository.getAllByRequestTypeAndCreator_UserId(requestType, id);
    }
    @Transactional
    public Request approveRequest(Request request, Integer id) {
        Medicine medicine = medicineService.getMedicineById(request.getMed().getId());
        User approvingUser = userService.getUserById(id);
        // Do we need to check for Customer/Employee Role here if we are using JWTs?
        if(approvingUser.getRole().equals(Role.EMPLOYEE)) {
            // Check if the medication has enough in stock before approving
            if(request.getRequestType().equals(RequestType.OPEN) && medicine.getStock() >= request.getDosageCount()) {
                //Create payment
                Payment newPayment = new Payment();
                newPayment.setAmount((float) (request.getDosageCount() * medicine.getPrice()));
                newPayment.setMedicineId(medicine);
                newPayment.setPayStatus(PayStatus.UNPAID);
                newPayment.setUserId(request.getCreator());
                newPayment.setReqId(request);
                Payment createdPayment = paymentService.createPayment(newPayment);

                // Update Stock
                int newStock = medicine.getStock() - request.getDosageCount();

                if (newStock <= 90 && newStock > 0) {
                    medicine.setStatus(Status.OUT_OF_STOCK);
                } else if (newStock <= 500 && newStock > 90) {
                    medicine.setStatus(Status.RUNNING_LOW);
                }

                medicine.setStock(newStock);
                medicineRepository.save(medicine);
                // Update Request
                request.setRequestType(RequestType.APPROVED);
                request.setPayment(createdPayment.getPaymentId());

                return requestRepository.save(request);
            }
        }
        return null;
    }

    public Request denyRequest(Request request, Integer id) {
        User approvingUser = userService.getUserById(id);
        // Do we need to check for Customer/Employee Role here if we are using JWTs?
        if(approvingUser.getRole().equals(Role.EMPLOYEE)) {
            if(request.getRequestType().equals(RequestType.OPEN)) {
                request.setRequestType(RequestType.DENIED);
                return requestRepository.save(request);
            }
        }
        return null;
    }

}
