package com.revature.service;

import com.revature.model.Request;
import com.revature.model.enums.RequestType;
import com.revature.repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Request createRequest(Request request) {
        // CHECK HERE IF THE MEDICINE IS AVAILABLE
        //if(MEDSERVICE GETSTATUS ISEQUAL TO INSTOCK)
        return requestRepository.save(request);
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
        requestRepository.delete(request);
    }

    public Integer requestCount() {
        return Math.toIntExact(requestRepository.count());
    }

    public List<Request> getAllByRequestType(RequestType requestType) {
        return requestRepository.getAllByRequestType(requestType);
    }

    //public List<Request> getAllByRequestByUser(User user)

    //public Request approveRequest(Request request) {}

    //public Request denyRequest(Request request) {}

    // TODO FOR APPROVING REQUESTS
    //  IF A MEDICATION IS APPROVED WE WANT TO SUBTRACT THE DOSAGECOUNT FROM THE AMOUNTINSTOCK FROM THE MEDICINE MODEL
}
