package com.revature.controller;

import com.revature.model.Payment;
import com.revature.model.User;
import com.revature.model.enums.PayStatus;
import com.revature.service.PaymentService;
import com.revature.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){this.paymentService = paymentService;}

    @PostMapping
    public Payment createPayment(@RequestBody Payment payment){
        return paymentService.createPayment(payment);
    }

    @GetMapping
    public List<Payment> getAllPayments(){
        return paymentService.getAllPayment();
    }

    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable int id){
        return paymentService.getPaymentById(id);
    }

    @GetMapping("/paystatus/{Status}")
    public List<Payment> getAllByPayStatus(@PathVariable("Status") String payStatus){
        PayStatus payStatus1 = PayStatus.valueOf(payStatus.toUpperCase(Locale.ROOT));
        return paymentService.getAllByPayStatus(payStatus1);
    }

    @GetMapping("/userid/{Id}")
    public List<Payment> getAllByUserId(@PathVariable("Id") String id){
        return paymentService.getAllByUserId(Integer.parseInt(id));
    }
}
