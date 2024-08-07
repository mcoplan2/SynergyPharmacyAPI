package com.revature;

import com.revature.model.Medicine;
import com.revature.model.Payment;
import com.revature.model.Request;
import com.revature.model.User;
import com.revature.repository.MedicineRepository;
import com.revature.repository.PaymentRepository;
import com.revature.repository.RequestRepository;
import com.revature.repository.UserRepository;
import com.revature.service.MedicineService;
import com.revature.service.PaymentService;
import com.revature.service.RequestService;
import com.revature.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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

    @Test
    public void testFindExistingPayment() {
        // Arrange
        User user = new User();
        user.setUserId(1);

        Medicine medicine = new Medicine();
        medicine.setId(2);

        Request request = new Request();
        request.setDosageCount(2);
        request.setDosageFreq(3);

        Payment payment = new Payment();
        payment.setUserId(user);
        payment.setMedicineId(medicine);
        payment.setReqId(request);
        payment.setPaymentId(3);

        Payment existingPayment = new Payment();
        Mockito.when(paymentRepository.findByUserId_UserIdAndMedicineId_MedIdAndReqId_DosageCountAndReqId_DosageFreq(
                user.getUserId(),
                medicine.getId(),
                request.getDosageCount(),
                request.getDosageFreq()
        )).thenReturn(Optional.of(existingPayment));

        Mockito.when(paymentRepository.findById(payment.getPaymentId())).thenReturn(Optional.of(payment));
        Mockito.when(paymentRepository.save(payment)).thenReturn(existingPayment);

        // Act
        Optional<Payment> result = Optional.ofNullable(paymentService.createPayment(payment));

        // Assert
        Assertions.assertEquals(existingPayment, result.get());
    }

}
