package com.revature;

import com.revature.model.Medicine;
import com.revature.model.Request;
import com.revature.model.User;
import com.revature.model.enums.RequestType;
import com.revature.model.enums.Role;
import com.revature.model.enums.Status;
import com.revature.model.enums.Type;
import com.revature.repository.MedicineRepository;
import com.revature.repository.RequestRepository;
import com.revature.service.MedicineService;
import com.revature.service.RequestService;
import com.revature.service.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RequestServiceUnitTests {

    private RequestService requestService;
    private RequestRepository requestRepository;
    private MedicineService medicineService;
    private MedicineRepository medicineRepository;
    private UserService userService;

    @BeforeEach
    public void setup() {
        requestRepository = Mockito.mock(RequestRepository.class);
        medicineRepository = Mockito.mock(MedicineRepository.class);
        medicineService = Mockito.mock(MedicineService.class);
        userService = Mockito.mock(UserService.class);
        requestService = new RequestService(requestRepository, medicineRepository, medicineService, userService);
    }

    User user =  new User().setUserId(1).setFirstName("Test").setLastName("Test").setPassWord("Test").setRole(Role.CUSTOMER);
    User user2 =  new User().setUserId(2).setFirstName("Test2").setLastName("Test2").setPassWord("Test2").setRole(Role.EMPLOYEE);

    Medicine medicine = new Medicine(1, "hello", 30, 2.9, Type.PILL, Status.IN_STOCK);
    Request request = new Request(1, 2, 2, user, medicine,RequestType.OPEN);

    Medicine medicine2 = new Medicine(1, "hello", 60, 2.9, Type.PILL, Status.OUT_OF_STOCK);
    Medicine medicine3 = new Medicine(1, "hello", 35, 2.9, Type.PILL, Status.IN_STOCK);
    Request request2 = new Request(2, 25, 2, user, medicine2,RequestType.OPEN);

    Request request3 = new Request(2, 25, 2, user, medicine2,RequestType.APPROVED);
    Request request4 = new Request(2, 2, 2, user, medicine2,RequestType.DENIED);

    @Test
    public void whenCreateRequestCalledDoesNotThrowException() {
        Assertions.assertDoesNotThrow(() -> requestService.createRequest(request));
    }

    @Test
    public void whenCreateRequestCalledIfMedicineIsNotInStockReturnsNull() {
        Assertions.assertNull(requestService.createRequest(request2));
    }

    @Test
    public void whenCreateRequestCalledReturnsCorrectRequestObject() {
        Mockito.when(requestRepository.save(request)).thenReturn(request);
        Request testRequest = requestService.createRequest(request);
        Assertions.assertEquals(request, testRequest);
    }

    @Test
    public void whenGetAllRequestsIsCalledDoesNotThrowException() {
        Assertions.assertDoesNotThrow(() -> requestService.getAllRequests());
    }

    @Test
    public void whenGetRequestByIdIsCalledDoesNotThrowException() {
        Mockito.when(requestRepository.findById(1)).thenReturn(Optional.ofNullable(request2));
        Assertions.assertDoesNotThrow(() -> requestService.getRequestById(1));
    }

    @Test
    public void whenGetRequestByIdIsCalledReturnsCorrectRequestObject() {
        Mockito.when(requestRepository.findById(1)).thenReturn(Optional.ofNullable(request2));
        Request testRequest = requestService.getRequestById(1);
        Assertions.assertEquals(request2, testRequest);
    }

    @Test
    public void whenUpdateRequestIsCalledDoesNotThrowException() {
        Mockito.when(requestRepository.findById(1)).thenReturn(Optional.ofNullable(request));
        Assertions.assertDoesNotThrow(() -> requestService.updateRequest(request));
    }

    @Test
    public void whenUpdateRequestIsCalledReturnsUpdatedRequestObject() {
        Mockito.when(requestRepository.findById(1)).thenReturn(Optional.ofNullable(request));
        Mockito.when(requestRepository.save(request)).thenReturn(request2);
        Request testRequest = requestService.updateRequest(request);
        Assertions.assertEquals(testRequest, request2);
    }

    @Test
    public void whenDeleteRequestIsCalledDoesNotThrowException() {
        Mockito.when(requestRepository.findById(1)).thenReturn(Optional.ofNullable(request));
        Assertions.assertDoesNotThrow(() -> requestService.deleteRequest(request));
    }

    @Test
    public void whenGetAllByRequestTypeIsCalledDoesNotThrowException() {
        Assertions.assertDoesNotThrow(() -> requestService.getAllByRequestType(RequestType.OPEN));
    }

    @Test
    public void whenGetAllByRequestTypeIsCalledReturnsCorrectRequestList() {
        List<Request> requests = new ArrayList<>();
        requests.add(request);
        requests.add(request2);
        Mockito.when(requestRepository.getAllByRequestType(RequestType.OPEN)).thenReturn(requests);
        List<Request> testRequests = requestService.getAllByRequestType(RequestType.OPEN);

        Assertions.assertEquals(requests, testRequests);
    }

    @Test
    public void whenGetAllByRequestByUserIsCalledDoesNotThrowException() {
        Assertions.assertDoesNotThrow(() -> requestService.getAllByRequestByUser(1));
    }

    @Test
    public void whenGetAllByRequestByUserIsCalledReturnsCorrectRequestList() {
        List<Request> requests = new ArrayList<>();
        requests.add(request);
        requests.add(request2);
        Mockito.when(requestRepository.getAllByCreator_UserId(1)).thenReturn(requests);
        List<Request> testRequests = requestService.getAllByRequestByUser(1);

        Assertions.assertEquals(requests.size(), testRequests.size());
    }

    @Test
    public void whenApproveRequestIsCalledDoesNotThrowException() {
        Mockito.when(userService.getUserById(1)).thenReturn(user2);
        Mockito.when(medicineService.getMedicineById(request.getMed().getId())).thenReturn(medicine);
        Assertions.assertDoesNotThrow(() -> requestService.approveRequest(request,1 ));
    }

    @Test
    public void whenApproveRequestIsCalledByEmployeeReturnsUpdatedRequestType() {
        Mockito.when(userService.getUserById(2)).thenReturn(user2);
        Mockito.when(medicineService.getMedicineById(request2.getMed().getId())).thenReturn(medicine2);
        Mockito.when(medicineRepository.save(medicine2)).thenReturn(medicine3);
        Mockito.when(requestRepository.save(request2)).thenReturn(request3);
        Request testRequest = requestService.approveRequest(request2, 2);
        Assertions.assertEquals(request2, testRequest);
        Assertions.assertEquals(request2.getRequestType(), testRequest.getRequestType());
    }

    @Test
    public void whenApproveRequestIsCalledByCustomerReturnsNull() {
        Mockito.when(userService.getUserById(2)).thenReturn(user);
        Request testRequest = requestService.approveRequest(request2, 2);
        Assertions.assertNull(testRequest);
    }

    @Test
    public void whenDenyRequestIsCalledDoesNotThrowException() {
        Mockito.when(userService.getUserById(1)).thenReturn(user2);
        Assertions.assertDoesNotThrow(() -> requestService.denyRequest(request,1 ));
    }

    @Test
    public void whenDenyRequestIsCalledByEmployeeReturnsUpdatedRequestType() {
        Mockito.when(userService.getUserById(2)).thenReturn(user2);
        Mockito.when(requestRepository.save(request2)).thenReturn(request4);
        Request testRequest = requestService.denyRequest(request2, 2);
        Assertions.assertEquals(request4, testRequest);
        Assertions.assertEquals(request4.getRequestType(), testRequest.getRequestType());
    }

    @Test
    public void whenDenyRequestIsCalledByCustomerReturnsNull() {
        Mockito.when(userService.getUserById(1)).thenReturn(user);
        Request testRequest = requestService.denyRequest(request2, 1);
        Assertions.assertNull(testRequest);
    }


}
