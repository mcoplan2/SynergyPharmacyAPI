package com.revature;

import com.revature.model.Medicine;
import com.revature.model.Request;
import com.revature.model.User;
import com.revature.model.enums.RequestType;
import com.revature.model.enums.Role;
import com.revature.model.enums.Status;
import com.revature.model.enums.Type;
import com.revature.service.RequestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class RequestServiceUnitTests {

    private final RequestService requestService = Mockito.mock(RequestService.class);

    User user =  new User().setUserId(1).setFirstName("Test").setLastName("Test").setPassWord("Test").setRole(Role.CUSTOMER);
    User user2 =  new User().setUserId(2).setFirstName("Test2").setLastName("Test2").setPassWord("Test2").setRole(Role.EMPLOYEE);

    Medicine medicine = new Medicine(1, "hello", 2, 2.9, Type.PILL, Status.IN_STOCK);
    Request request = new Request(1, 2, 2, user, medicine,RequestType.OPEN);

    Medicine medicine2 = new Medicine(1, "hello", 60, 2.9, Type.PILL, Status.OUT_OF_STOCK);
    Request request2 = new Request(2, 25, 2, user, medicine2,RequestType.OPEN);

    Request request3 = new Request(2, 2, 2, user, medicine2,RequestType.APPROVED);
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
        Mockito.when(requestService.createRequest(request)).thenReturn(request);
        Request testRequest = requestService.createRequest(request);
        Assertions.assertEquals(request, testRequest);
    }

    @Test
    public void whenGetAllRequestsIsCalledDoesNotThrowException() {
        Assertions.assertDoesNotThrow(() -> requestService.getAllRequests());
    }

    @Test
    public void whenGetRequestByIdIsCalledDoesNotThrowException() {
        Assertions.assertDoesNotThrow(() -> requestService.getRequestById(0));
    }

    @Test
    public void whenGetRequestByIdIsCalledReturnsCorrectRequestObject() {
        Mockito.when(requestService.getRequestById(0)).thenReturn(request);
        Request testRequest = requestService.getRequestById(0);
        Assertions.assertEquals(request, testRequest);
    }

    @Test
    public void whenUpdateRequestIsCalledDoesNotThrowException() {
        Assertions.assertDoesNotThrow(() -> requestService.updateRequest(request));
    }

    @Test
    public void whenUpdateRequestIsCalledReturnsUpdatedRequestObject() {
        Mockito.when(requestService.updateRequest(request)).thenReturn(request2);
        Request testRequest = requestService.updateRequest(request);
        Assertions.assertEquals(testRequest, request2);
    }

    @Test
    public void whenDeleteRequestIsCalledDoesNotThrowException() {
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
        Mockito.when(requestService.getAllByRequestType(RequestType.OPEN)).thenReturn(requests);
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
        Mockito.when(requestService.getAllByRequestByUser(1)).thenReturn(requests);
        List<Request> testRequests = requestService.getAllByRequestByUser(1);

        Assertions.assertEquals(requests, testRequests);
    }

    @Test
    public void whenApproveRequestIsCalledDoesNotThrowException() {
        Assertions.assertDoesNotThrow(() -> requestService.approveRequest(request,1 ));
    }

    @Test
    public void whenApproveRequestIsCalledByEmployeeReturnsUpdatedRequestType() {
        Mockito.when(requestService.approveRequest(request2,2)).thenReturn(request3);
        Request testRequest = requestService.approveRequest(request2, 2);
        Assertions.assertEquals(request3, testRequest);
        Assertions.assertEquals(request3.getRequestType(), testRequest.getRequestType());
    }

    @Test
    public void whenApproveRequestIsCalledByCustomerReturnsNull() {
        Mockito.when(requestService.approveRequest(request2,1)).thenReturn(null);
        Request testRequest = requestService.approveRequest(request2, 1);
        Assertions.assertNull(testRequest);
    }

    @Test
    public void whenDenyRequestIsCalledDoesNotThrowException() {
        Assertions.assertDoesNotThrow(() -> requestService.denyRequest(request,1 ));
    }

    @Test
    public void whenDenyRequestIsCalledByEmployeeReturnsUpdatedRequestType() {
        Mockito.when(requestService.denyRequest(request2,2)).thenReturn(request4);
        Request testRequest = requestService.denyRequest(request2, 2);
        Assertions.assertEquals(request4, testRequest);
        Assertions.assertEquals(request4.getRequestType(), testRequest.getRequestType());
    }

    @Test
    public void whenDenyRequestIsCalledByCustomerReturnsNull() {
        Mockito.when(requestService.denyRequest(request2,1)).thenReturn(null);
        Request testRequest = requestService.denyRequest(request2, 1);
        Assertions.assertNull(testRequest);
    }


}
