package com.revature.service;

import com.revature.model.Medicine;
import com.revature.model.Request;
import com.revature.model.User;
import com.revature.model.enums.RequestType;
import com.revature.model.enums.Role;
import com.revature.model.enums.Status;
import com.revature.repository.MedicineRepository;
import com.revature.repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final MedicineRepository medicineRepository;
    private final MedicineService medicineService;
    private final UserService userService;

    public RequestService(RequestRepository requestRepository, MedicineRepository medicineRepository, MedicineService medicineService, UserService userService) {
        this.requestRepository = requestRepository;
        this.medicineRepository = medicineRepository;
        this.medicineService = medicineService;
        this.userService = userService;
    }

    public Request createRequest(Request request) throws IllegalArgumentException {
        if(request.getMed().getStatus() != Status.OUT_OF_STOCK) {
            return requestRepository.save(request);
        }
        return null;
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

    public Request approveRequest(Request request, Integer id) {
        Medicine medicine = medicineService.getMedicineById(request.getMed().getId());
        User approvingUser = userService.getUserById(id);
        // Do we need to check for Customer/Employee Role here if we are using JWTs?
        if(approvingUser.getRole().equals(Role.EMPLOYEE)) {
            // Check if the medication has enough in stock before approving
            if(request.getRequestType().equals(RequestType.OPEN) && medicine.getStock() >= request.getDosageCount()) {
                request.setRequestType(RequestType.APPROVED);
                int newStock = medicine.getStock() - request.getDosageCount();
                medicine.setStock(newStock);
                request.getMed().setStock(newStock);
                medicineRepository.save(medicine);
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
