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
    private Request req;

    @OneToOne
    private User user;

    @OneToOne
    private Medication med;

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

    public Payment(Integer paymentId, Float amount, PayStatus payStatus, Request req, User user, Medication medication) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.payStatus = payStatus;
        this.req = req;
        this.user = user;
        this.med = medication;
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
        return req;
    }

    public Payment setReqId(Request req) {
        this.req = req;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Payment setUser(User user) {
        this.user = user;
        return this;
    }

    public Medication getMedicationId() {
        return med;
    }

    public Payment setMedicationId(Medication medication) {
        this.med = medication;
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
        return Objects.equals(paymentId, payment.paymentId) && Objects.equals(amount, payment.amount) && payStatus == payment.payStatus && Objects.equals(req, payment.req) && Objects.equals(user, payment.user) && Objects.equals(med, payment.med) && Objects.equals(creationDate, payment.creationDate) && Objects.equals(updateDate, payment.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, amount, payStatus, req, user, med, creationDate, updateDate);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", amount=" + amount +
                ", payStatus=" + payStatus +
                ", req=" + req +
                ", user=" + user +
                ", med=" + med +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                '}';
    }
}


