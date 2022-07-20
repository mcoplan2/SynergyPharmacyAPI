package com.revature.service;

import com.revature.exception.UserNotFoundException;
import com.revature.model.Payment;
import com.revature.model.User;
import com.revature.model.enums.PayStatus;
import com.revature.repository.PaymentRepository;
import com.revature.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public PaymentService(PaymentRepository paymentRepository, UserRepository userRepository){
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

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

    public List<Payment> getAllByUserId(Integer id){
        User userId = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return paymentRepository.getAllByUserId(userId);
    }
}
