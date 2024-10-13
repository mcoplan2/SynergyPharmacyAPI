package com.revature;

import com.revature.model.Medication;
import com.revature.model.Payment;
import com.revature.model.Request;
import com.revature.model.User;
import com.revature.model.enums.*;
import com.revature.repository.PaymentRepository;
import com.revature.repository.UserRepository;
import com.revature.service.PaymentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class PaymentServiceUnitTests {

    private PaymentService paymentService;
    private PaymentRepository paymentRepository;
    private UserRepository userRepository;


    @BeforeEach
    public void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        paymentRepository = Mockito.mock(PaymentRepository.class);
        paymentService = new PaymentService(paymentRepository, userRepository);
    }

    User user = new User("user","fname","lname","pass", Role.CUSTOMER);
    Medication medication = new Medication(1, "hello", 30, 2.9, Type.PILL, Status.IN_STOCK);
    Request request = new Request(1, 2, 2, user, medication, RequestType.OPEN);
    Payment payment = new Payment(1,200.00F, PayStatus.FULLY_PAID,request,user,medication);
    Payment payment2 = new Payment(2,300.00F, PayStatus.FULLY_PAID,request,user,medication);


    @Test
    public void whenCreatePaymentIsCalledDoesNotThrowException() {
        Mockito.when(paymentRepository.findByUser_UserIdAndMed_MedIdAndReq_DosageCountAndReq_DosageFreq(
                payment.getUser().getUserId(),
                payment.getMedicationId().getId(),
                payment.getReqId().getDosageCount(),
                payment.getReqId().getDosageFreq())).thenReturn(Optional.empty());
        Mockito.when(paymentRepository.save(payment)).thenReturn(payment);
        Assertions.assertDoesNotThrow(()-> paymentService.createPayment(payment));
    }

    @Test
    public void whenCreatePaymentIsCalledWithExistingPaymentUpdatePaymentIsCalled() {
        Mockito.when(paymentRepository.findByUser_UserIdAndMed_MedIdAndReq_DosageCountAndReq_DosageFreq(
                payment.getUser().getUserId(),
                payment.getMedicationId().getId(),
                payment.getReqId().getDosageCount(),
                payment.getReqId().getDosageFreq())).thenReturn(Optional.ofNullable(payment));
        Mockito.when(paymentRepository.findById(1)).thenReturn(Optional.ofNullable(payment2));
        Payment payment1 = paymentService.createPayment(payment2);
        Assertions.assertNotEquals(payment1, payment);
    }

    @Test
    public void whenGetAllPaymentsIsCalledDoesNotThrowException() {
        Assertions.assertDoesNotThrow(()-> paymentService.getAllPayment() );
    }

    @Test
    public void whenGetAllPaymentsIsCalledReturnsListOfPayments() {
        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);
        paymentList.add(payment2);
        Mockito.when(paymentRepository.findAll()).thenReturn(paymentList);
        List<Payment> returnedList = paymentService.getAllPayment();
        Assertions.assertEquals(paymentList, returnedList);
    }

    @Test
    public void whenGetPaymentByIdIsCalledDoesNotThrowException() {
        Mockito.when(paymentRepository.findById(1)).thenReturn(Optional.ofNullable(payment));
        Assertions.assertDoesNotThrow(()-> paymentService.getPaymentById(1));
    }

    @Test
    public void whenGetPaymentByIdIsCalledReturnsPayment() {
        Mockito.when(paymentRepository.findById(1)).thenReturn(Optional.ofNullable(payment));
        Payment returnedPayment = paymentService.getPaymentById(1);
        Assertions.assertEquals(payment, returnedPayment);
    }

    @Test
    public void whenGetAllByPayStatusIsCalledDoesNotThrowException() {
        Assertions.assertDoesNotThrow(()-> paymentService.getAllByPayStatus(PayStatus.FULLY_PAID));
    }

    @Test
    public void whenGetAllByPayStatusIsCalledReturnsListOfPayments() {
        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);
        paymentList.add(payment2);
        Mockito.when(paymentRepository.getAllByPayStatus(PayStatus.FULLY_PAID)).thenReturn(paymentList);
        List<Payment> returnedList = paymentService.getAllByPayStatus(PayStatus.FULLY_PAID);
        Assertions.assertEquals(paymentList, returnedList);
    }

    @Test
    public void whenGetAllByUserIdIsCalledDoesNotThrowException() {
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        Assertions.assertDoesNotThrow(()-> paymentService.getAllByUserId(1));
    }

    @Test
    public void whenGetAllByUserIdIsCalledReturnsListOfPayments() {
        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);
        paymentList.add(payment2);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        Mockito.when(paymentRepository.getAllByUser_UserId(user.getUserId())).thenReturn(paymentList);
        List<Payment> returnedList = paymentService.getAllByUserId(1);
        Assertions.assertEquals(paymentList, returnedList);
    }

    @Test
    public void whenGetAllByUserIdAndPayStatusIsCalledDoesNotThrowException() {
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        Assertions.assertDoesNotThrow(()-> paymentService.getAllByUserIdAndStatus(1, PayStatus.UNPAID));
    }

    @Test
    public void whenGetAllByUserIdAndPayStatusIsCalledReturnsListOfPayments() {
        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);
        paymentList.add(payment2);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        Mockito.when(paymentRepository.getAllByUser_UserIdAndPayStatus(user.getUserId(), PayStatus.FULLY_PAID)).thenReturn(paymentList);
        List<Payment> returnedList = paymentService.getAllByUserIdAndStatus(1, PayStatus.FULLY_PAID);
        Assertions.assertEquals(paymentList, returnedList);
    }

}
