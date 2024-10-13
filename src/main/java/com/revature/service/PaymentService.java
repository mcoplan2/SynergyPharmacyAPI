package com.revature.service;

import com.revature.exception.UserNotFoundException;
import com.revature.model.Payment;
import com.revature.model.User;
import com.revature.model.enums.PayStatus;
import com.revature.repository.PaymentRepository;
import com.revature.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public PaymentService(PaymentRepository paymentRepository, UserRepository userRepository){
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    public Payment  createPayment(Payment payment){
        Optional<Payment> existingPayment = paymentRepository.findByUser_UserIdAndMed_MedIdAndReq_DosageCountAndReq_DosageFreq(
                payment.getUser().getUserId(),
                payment.getMedicationId().getId(),
                payment.getReqId().getDosageCount(),
                payment.getReqId().getDosageFreq()
        );

        if(existingPayment.isPresent()) {
            Payment paymentToChange = existingPayment.get();
            paymentToChange.setPayStatus(PayStatus.UNPAID);
            return updatePayment(paymentToChange);
        }
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
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return paymentRepository.getAllByUser_UserId(user.getUserId());
    }

    public List<Payment> getAllByUserIdAndStatus(Integer userId, PayStatus status) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return paymentRepository.getAllByUser_UserIdAndPayStatus(user.getUserId(), status);
    }

    public void deletePayment(Payment payment){
        paymentRepository.delete(payment);
    }

    public Payment updatePayment(Payment payment) {
        Payment paymentToEdit = paymentRepository.findById(payment.getPaymentId()).orElseThrow(() -> new RuntimeException("Payment could not be found"));
        paymentToEdit.setAmount(payment.getAmount());
        paymentToEdit.setPayStatus(payment.getPayStatus());
        return paymentRepository.save(paymentToEdit);
    }
}
