package com.revature.model;

import com.revature.model.enums.PayStatus;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "payments")
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer paymentId;

    @Column(nullable = false)
    private Float amount;

    @Enumerated
    @Column(nullable = false)
    private PayStatus payStatus;

    @OneToOne
    private Request reqId;

    @OneToOne
    private User userId;

    @OneToOne
    private Medicine medicineId;

    public Payment(){}

    public Payment(Integer paymentId, Float amount, PayStatus payStatus, Request reqId, User userId, Medicine medicineId) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.payStatus = payStatus;
        this.reqId = reqId;
        this.userId = userId;
        this.medicineId = medicineId;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public Payment setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
        return this;
    }

    public Float getAmount() {
        return amount;
    }

    public Payment setAmount(Float amount) {
        this.amount = amount;
        return this;
    }

    public PayStatus getPayStatus() {
        return payStatus;
    }

    public Payment setPayStatus(PayStatus payStatus) {
        this.payStatus = payStatus;
        return this;
    }

    public Request getReqId() {
        return reqId;
    }

    public Payment setReqId(Request reqId) {
        this.reqId = reqId;
        return this;
    }

    public User getUserId() {
        return userId;
    }

    public Payment setUserId(User userId) {
        this.userId = userId;
        return this;
    }

    public Medicine getMedicineId() {
        return medicineId;
    }

    public Payment setMedicineId(Medicine medicineId) {
        this.medicineId = medicineId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(paymentId, payment.paymentId) && Objects.equals(amount, payment.amount) && payStatus == payment.payStatus && Objects.equals(reqId, payment.reqId) && Objects.equals(userId, payment.userId) && Objects.equals(medicineId, payment.medicineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, amount, payStatus, reqId, userId, medicineId);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", amount=" + amount +
                ", payStatus=" + payStatus +
                ", reqId=" + reqId +
                ", userId=" + userId +
                ", medicineId=" + medicineId +
                '}';
    }
}


