package com.revature.service;

import com.revature.model.Payment;
import com.revature.model.enums.PayStatus;
import com.revature.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository){this.paymentRepository = paymentRepository;}

    public Payment  createPayment(Payment payment){
        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayment(){
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(int id){
        return paymentRepository.findById(id).orElseThrow( () -> new RuntimeException("Payment could not be found"));
    }

    public List<Payment> getAllByPayStatus(PayStatus payStatus){
        return paymentRepository.getAllByPayStatus(payStatus);
    }
}
