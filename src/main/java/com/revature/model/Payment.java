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

    public Payment(){}

    public Payment(Integer paymentId, Float amount, PayStatus payStatus, Request reqId) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.payStatus = payStatus;
        this.reqId = reqId;
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

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", amount=" + amount +
                ", payStatus=" + payStatus +
                ", reqId=" + reqId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(paymentId, payment.paymentId) && Objects.equals(amount, payment.amount) && payStatus == payment.payStatus && Objects.equals(reqId, payment.reqId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, amount, payStatus, reqId);
    }
}


