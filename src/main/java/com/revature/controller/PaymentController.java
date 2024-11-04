package com.revature.controller;

import com.revature.model.Payment;
import com.revature.model.enums.PayStatus;
import com.revature.model.enums.RequestType;
import com.revature.model.enums.Status;
import com.revature.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = {"http://localhost:3000", "https://synergy-ui-next-js-ten.vercel.app:3000"}, allowedHeaders = "*")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){this.paymentService = paymentService;}

    @PostMapping
    @CrossOrigin
    public Payment createPayment(@RequestBody Payment payment){
        return paymentService.createPayment(payment);
    }

    @DeleteMapping
    @CrossOrigin
    public void deletePayment(@RequestBody Payment payment){
        paymentService.deletePayment(payment);
    }

    @GetMapping
    @CrossOrigin
    public List<Payment> getAllPayments(){
        return paymentService.getAllPayment();
    }

    @PostMapping("/update")
    @CrossOrigin
    public Payment updatePayment(@RequestBody Payment payment){return paymentService.updatePayment(payment);}

    @GetMapping("/{id}")
    @CrossOrigin
    public Payment getPaymentById(@PathVariable int id){
        return paymentService.getPaymentById(id);
    }

    @GetMapping("/paystatus/{Status}")
    @CrossOrigin
    public List<Payment> getAllByPayStatus(@PathVariable("Status") String payStatus){
        PayStatus payStatus1 = PayStatus.valueOf(payStatus.toUpperCase(Locale.ROOT));
        return paymentService.getAllByPayStatus(payStatus1);
    }

    @GetMapping("/userid/{Id}")
    @CrossOrigin
    public List<Payment> getAllByUserId(@PathVariable("Id") String id){
        return paymentService.getAllByUserId(Integer.parseInt(id));
    }

    @GetMapping("/userid/{Id}/paystatus/{status}")
    public List<Payment> getAllByUserIdAndStatus(@PathVariable("Id") String id, @PathVariable("status") String status){
        PayStatus statusTypeId = PayStatus.valueOf(status.toUpperCase(Locale.ROOT));
        return paymentService.getAllByUserIdAndStatus(Integer.parseInt(id), statusTypeId);
    }
}
