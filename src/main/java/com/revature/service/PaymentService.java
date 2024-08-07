package com.revature.service;

import com.revature.exception.UserNotFoundException;
import com.revature.model.Payment;
import com.revature.model.User;
import com.revature.model.enums.PayStatus;
import com.revature.repository.PaymentRepository;
import com.revature.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        Optional<Payment> existingPayment = paymentRepository.findByUserId_UserIdAndMedicineId_MedIdAndReqId_DosageCountAndReqId_DosageFreq(
                payment.getUserId().getUserId(),
                payment.getMedicineId().getId(),
                payment.getReqId().getDosageCount(),
                payment.getReqId().getDosageFreq()

        );
        System.out.println("TESTING \n");
        System.out.println(existingPayment);
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
        User userId = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return paymentRepository.getAllByUserId(userId);
    }

    public List<Payment> getAllByUserId(Integer id, String status){
        List<Payment> list = getAllByUserId(id);
        List<Payment> rList = new ArrayList<>();
        PayStatus payStatus = PayStatus.valueOf(status);
        System.err.println(list);
        for(Payment p: list){
            if(p.getPayStatus() == payStatus)
                rList.add(p);
        }
        return rList;
    }

    public void deletePayment(Payment payment){
        paymentRepository.delete(payment);
    }

    public Payment updatePayment(Payment payment) {
        Payment paymentToEdit = paymentRepository.findById(payment.getPaymentId()).orElseThrow(() -> new RuntimeException("Payment could not be found"));
        System.out.println("11111111111  \n");
        System.out.println("11111111111 \n");
        System.out.println("11111111111 \n");
        System.out.println("111111111111 \n");
        System.out.println(paymentToEdit);
        paymentToEdit.setAmount(payment.getAmount());
        paymentToEdit.setPayStatus(payment.getPayStatus());
        System.out.println(paymentToEdit);
        return paymentRepository.save(paymentToEdit);
    }
}
