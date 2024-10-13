package com.revature;

import com.revature.model.Medication;
import com.revature.model.Payment;
import com.revature.model.Request;
import com.revature.model.User;
import com.revature.model.enums.*;
import com.revature.repository.MedicationRepository;
import com.revature.repository.RequestRepository;
import com.revature.service.MedicationService;
import com.revature.service.PaymentService;
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
    private MedicationService medicationService;
    private MedicationRepository medicationRepository;
    private UserService userService;
    private PaymentService paymentService;

    @BeforeEach
    public void setup() {
        requestRepository = Mockito.mock(RequestRepository.class);
        medicationRepository = Mockito.mock(MedicationRepository.class);
        medicationService = Mockito.mock(MedicationService.class);
        userService = Mockito.mock(UserService.class);
        paymentService = Mockito.mock(PaymentService.class);
        requestService = new RequestService(requestRepository, medicationRepository, medicationService, userService, paymentService);
    }

    User user = new User("user","fname","lname","pass", Role.CUSTOMER);
    User user2 = new User("user","fname2","lname2","pass2", Role.EMPLOYEE);

    Medication medication = new Medication(1, "hello", 30, 2.9, Type.PILL, Status.IN_STOCK);
    Request request = new Request(1, 2, 2, user, medication,RequestType.OPEN);

    Medication medication2 = new Medication(1, "hello", 400, 2.9, Type.PILL, Status.OUT_OF_STOCK);
    Medication medication3 = new Medication(1, "hello", 35, 2.9, Type.PILL, Status.IN_STOCK);
    Request request2 = new Request(2, 25, 2, user, medication2,RequestType.OPEN);

    Request request3 = new Request(2, 25, 2, user, medication2,RequestType.APPROVED);
    Request request4 = new Request(2, 2, 2, user, medication2,RequestType.DENIED);

    Payment payment = new Payment(1,200.00F, PayStatus.FULLY_PAID,request,user,medication);

    @Test
    public void whenCreateRequestCalledDoesNotThrowException() {
        Mockito.when(medicationService.getMedicationById(1)).thenReturn(medication);
        Assertions.assertDoesNotThrow(() -> requestService.createRequest(request));
    }

    @Test
    public void whenCreateRequestCalledIfMedicationIsNotInStockThrowsError() {
        Mockito.when(medicationService.getMedicationById(1)).thenReturn(medication2);
        Assertions.assertThrows(RuntimeException.class, ()-> requestService.createRequest(request2));
    }

    @Test
    public void whenCreateRequestCalledReturnsCorrectRequestObject() {
        Mockito.when(requestRepository.save(request)).thenReturn(request);
        Mockito.when(medicationService.getMedicationById(1)).thenReturn(medication);
        Request testRequest = requestService.createRequest(request);
        Assertions.assertEquals(request, testRequest);
    }

    @Test
    public void whenCreateRequestCalledReturnsCorrectRequestObject2() {
        Mockito.when(requestRepository.save(request)).thenReturn(request);
        Mockito.when(medicationService.getMedicationById(1)).thenReturn(medication);
        Mockito.when(requestRepository.findByUser_UserIdAndMed_MedIdAndDosageCountAndDosageFreqAndRequestType(
                request.getUser().getUserId(),
                request.getMed().getId(),
                request.getDosageCount(),
                request.getDosageFreq(),
                RequestType.APPROVED
        )).thenReturn(Optional.ofNullable(request));
        Mockito.when(requestRepository.findById(1)).thenReturn(Optional.ofNullable(request));
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
        Mockito.when(requestRepository.getAllByUser_UserId(1)).thenReturn(requests);
        List<Request> testRequests = requestService.getAllByRequestByUser(1);

        Assertions.assertEquals(requests.size(), testRequests.size());
    }

    @Test
    public void whenGetRequestByUserAndTypeIsCalledDoesNotThrowException() {
        Assertions.assertDoesNotThrow(()-> requestService.getAllRequestByUserAndType(RequestType.APPROVED, 1));
    }

    @Test
    public void whenGetAllByUserAndTypeAndMedicationFirstLetterIsCalledDoesNotThrowException() {
        Assertions.assertDoesNotThrow(()-> requestService.getAllByUserAndTypeAndMedicationFirstLetter(1,RequestType.APPROVED,"l"));
    }

    @Test
    public void whenGetAllByUserAndTypeAndMedicationContaining() {
        Assertions.assertDoesNotThrow(()-> requestService.getAllByUserAndTypeAndMedicationContaining(1,RequestType.APPROVED,"ls"));
    }

    @Test
    public void whenGetAllByMedicationNameContaining() {
        Assertions.assertDoesNotThrow(()-> requestService.getAllByMedicationNameContaining("ls"));
    }

    @Test
    public void whenGetAllByMedicationNameStartingWith() {
        Assertions.assertDoesNotThrow(()-> requestService.getAllByMedicationNameStartingWith("l"));
    }

    @Test
    public void whenApproveRequestIsCalledDoesNotThrowException() {
        Mockito.when(userService.getUserById(1)).thenReturn(user2);
        Mockito.when(medicationService.getMedicationById(request.getMed().getId())).thenReturn(medication);
        Mockito.when(paymentService.createPayment(Mockito.any())).thenReturn(payment);
        Mockito.when(requestRepository.save(request)).thenReturn(request);
        Assertions.assertDoesNotThrow(() -> requestService.approveRequest(request,1 ));
    }

    @Test
    public void whenApproveRequestIsCalledByEmployeeReturnsUpdatedRequestType() {
        Mockito.when(userService.getUserById(2)).thenReturn(user2);
        Mockito.when(medicationService.getMedicationById(request2.getMed().getId())).thenReturn(medication2);
        Mockito.when(medicationRepository.save(medication2)).thenReturn(medication3);
        Mockito.when(requestRepository.save(request2)).thenReturn(request3);
        Mockito.when(paymentService.createPayment(Mockito.any())).thenReturn(payment);
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
