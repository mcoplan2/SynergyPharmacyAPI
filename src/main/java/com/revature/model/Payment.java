package com.revature.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.revature.model.enums.PayStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
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

    @DateTimeFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    @CreationTimestamp
    @Column(name = "creation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime creationDate;

    @DateTimeFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    @UpdateTimestamp
    @Column(name = "update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime updateDate;

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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Payment setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public Payment setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(paymentId, payment.paymentId) && Objects.equals(amount, payment.amount) && payStatus == payment.payStatus && Objects.equals(reqId, payment.reqId) && Objects.equals(userId, payment.userId) && Objects.equals(medicineId, payment.medicineId) && Objects.equals(creationDate, payment.creationDate) && Objects.equals(updateDate, payment.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, amount, payStatus, reqId, userId, medicineId, creationDate, updateDate);
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
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                '}';
    }
}


