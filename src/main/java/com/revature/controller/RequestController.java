package com.revature.controller;

import com.revature.model.Request;
import com.revature.model.enums.RequestType;
import com.revature.service.RequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/requests")
@CrossOrigin(origins = "https://synergypharmacy.vercel.app" ,allowedHeaders = "*")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    @CrossOrigin// POST /requests
    public Request createRequest(@RequestBody Request request) {
        return requestService.createRequest(request);
    }

    @GetMapping
    @CrossOrigin// GET /requests
    public List<Request> getAllRequests() {
        return requestService.getAllRequests();
    }

    @GetMapping("/filter")
    public List<Request> getAllRequestsByMedicationFirstLetterOrContains(String letter, String query) {
        if (letter != null && !letter.isEmpty()) {
            if (query != null && !query.isEmpty()) {
                return requestService.getAllByMedicationNameContaining(query);
            } else {
                return requestService.getAllByMedicationNameStartingWith(letter);
            }
        } else {
            if (query != null && !query.isEmpty()) {
                return requestService.getAllByMedicationNameContaining(query);
            } else {
                return requestService.getAllRequests();
            }
        }
    }

    @GetMapping("/{id}") // GET /requests/{ID}
    public Request getRequestById(@PathVariable Integer id) {
        return requestService.getRequestById(id);
    }

    @PutMapping
    public Request updateRequest(@RequestBody Request request) {
        return requestService.updateRequest(request);
    }

    @DeleteMapping
    public void deleteRequest(@RequestBody Request request) {
        requestService.deleteRequest(request);
    }

    @GetMapping("/type/{requestType}")
    public List<Request> getAllByRequestType(@PathVariable("requestType") String requestType) {
        RequestType requestTypeId = RequestType.valueOf(requestType.toUpperCase(Locale.ROOT));
        return requestService.getAllByRequestType(requestTypeId);
    }

    @GetMapping("/user/{id}/type/{requestType}")
    public List<Request> getAllByUserAndType(@PathVariable("requestType") String requestType, @PathVariable("id") Integer id) {
        RequestType requestTypeId = RequestType.valueOf(requestType.toUpperCase(Locale.ROOT));
        return requestService.getAllRequestByUserAndType(requestTypeId, id);
    }

    @GetMapping("/user/{id}")
    public List<Request> getAllRequestByUser(@PathVariable("id") Integer id) {
        return requestService.getAllByRequestByUser(id);
    }

    @GetMapping("/user/{id}/type/{requestType}/medication/filter")
    public List<Request> getAllByUserAndTypeAndMedicationFirstLetter(@PathVariable("id") Integer id, @PathVariable("requestType") String requestType, String letter, String query) {
        RequestType requestTypeId = RequestType.valueOf(requestType.toUpperCase(Locale.ROOT));
        if (letter != null && !letter.isEmpty()) {
            if(query != null && !query.isEmpty()) {
                return requestService.getAllByUserAndTypeAndMedicationContaining(id, requestTypeId, query);
            } else {
                return requestService.getAllByUserAndTypeAndMedicationFirstLetter(id, requestTypeId, letter);
            }
        } else {
            if(query != null && !query.isEmpty()) {
                return requestService.getAllByUserAndTypeAndMedicationContaining(id, requestTypeId, query);
            }
            else {
                return requestService.getAllRequestByUserAndType(requestTypeId, id);
            }
        }
    }

    @PostMapping("/approve/{id}")
    public Request approveRequest(@RequestBody Request request , @PathVariable("id") Integer id) {
        return requestService.approveRequest(request, id);
    }

    @PostMapping("/deny/{id}")
    public Request denyRequest(@RequestBody Request request , @PathVariable("id") Integer id) {
        return requestService.denyRequest(request, id);
    }

}
