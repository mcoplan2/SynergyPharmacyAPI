package com.revature.service;

import com.revature.exception.ResourceCouldNotBeCreatedException;
import com.revature.model.Medication;
import com.revature.model.Payment;
import com.revature.model.Request;
import com.revature.model.User;
import com.revature.model.enums.PayStatus;
import com.revature.model.enums.RequestType;
import com.revature.model.enums.Role;
import com.revature.model.enums.Status;
import com.revature.repository.MedicationRepository;
import com.revature.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final MedicationRepository medicationRepository;
    private final MedicationService medicationService;
    private final UserService userService;
    private final PaymentService paymentService;

    @Autowired
    public RequestService(RequestRepository requestRepository, MedicationRepository medicationRepository, MedicationService medicationService, UserService userService, PaymentService paymentService) {
        this.requestRepository = requestRepository;
        this.medicationRepository = medicationRepository;
        this.medicationService = medicationService;
        this.userService = userService;
        this.paymentService = paymentService;
    }

    public Request createRequest(Request request)  {
        Medication medication = medicationService.getMedicationById(request.getMed().getId());

        if(medication.getStatus() != Status.OUT_OF_STOCK) {
            Optional<Request> existingRequest = requestRepository.findByUser_UserIdAndMed_MedIdAndDosageCountAndDosageFreqAndRequestType(
                    request.getUser().getUserId(),
                    request.getMed().getId(),
                    request.getDosageCount(),
                    request.getDosageFreq(),
                    RequestType.APPROVED
            );
            if(existingRequest.isPresent()) {
                Request requestToUpdate = existingRequest.get();
                requestToUpdate.setRequestType(RequestType.OPEN);
                return updateRequest(requestToUpdate);
            }
            return requestRepository.save(request);
        }
        throw new RuntimeException(medication.getName()+" is Out of Stock");
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
        return requestRepository.getAllByUser_UserId(id);
    }

    public List<Request> getAllRequestByUserAndType(RequestType requestType, Integer id) {
        return requestRepository.getAllByRequestTypeAndUser_UserId(requestType, id);
    }

    public List<Request> getAllByUserAndTypeAndMedicationFirstLetter(Integer id, RequestType requestType, String letter) {
        return requestRepository.findByUser_UserIdAndRequestTypeAndMed_NameStartingWith(id, requestType, letter);
    }

    public List<Request> getAllByUserAndTypeAndMedicationContaining(Integer id, RequestType requestType, String query) {
        return requestRepository.findByUser_UserIdAndRequestTypeAndMed_NameContainingIgnoreCase(id, requestType, query);
    }

    public List<Request> getAllByMedicationNameContaining(String query) {
        return requestRepository.findByMed_NameContainingIgnoreCase(query);
    }

    public List<Request> getAllByMedicationNameStartingWith(String letter) {
        return  requestRepository.findByMed_NameStartingWith(letter);
    }


    @Transactional
    public Request approveRequest(Request request, Integer id) {
        Medication medication = medicationService.getMedicationById(request.getMed().getId());
        User approvingUser = userService.getUserById(id);

        if(approvingUser.getRole().equals(Role.EMPLOYEE)) {
            // Check if the medication has enough in stock before approving
            if(request.getRequestType().equals(RequestType.OPEN) && medication.getStock() >= request.getDosageCount()) {
                //Create payment
                Payment newPayment = new Payment();
                newPayment.setAmount((float) (request.getDosageCount() * medication.getPrice()));
                newPayment.setMedicationId(medication);
                newPayment.setPayStatus(PayStatus.UNPAID);
                newPayment.setUser(request.getUser());
                newPayment.setReqId(request);
                Payment createdPayment = paymentService.createPayment(newPayment);

                // Update Stock
                int newStock = medication.getStock() - request.getDosageCount();

                if (newStock <= 90 && newStock > 0) {
                    medication.setStatus(Status.OUT_OF_STOCK);
                } else if (newStock <= 500 && newStock > 90) {
                    medication.setStatus(Status.RUNNING_LOW);
                }

                medication.setStock(newStock);
                medicationRepository.save(medication);
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
