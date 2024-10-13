package com.revature.model;

import com.revature.model.enums.RequestType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "requests")
public class Request implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer requestId;

    @Column(nullable = false)
    private Integer dosageCount; // amount of pills the user will receive on their prescription.

    @Column(nullable = false)
    private Integer dosageFreq; // number of times per day they need to take this pill

    @OneToOne
    private User user;

    @OneToOne
    private Medication med;

    private Integer paymentId;

    @Enumerated
    private RequestType requestType = RequestType.OPEN;


    public Request() {
    }

    public Request(Integer id, Integer dosageCount, Integer dosageFreq, User user, Medication medication ,RequestType requestType) {
        this.requestId = id;
        this.dosageCount = dosageCount;
        this.dosageFreq = dosageFreq;
        this.med = medication;
        this.user = user;
        this.requestType = requestType;
    }

    public Request(Integer id, Integer dosageCount, Integer dosageFreq, User user, Medication medication ,RequestType requestType, Integer paymentId) {
        this.requestId = id;
        this.dosageCount = dosageCount;
        this.dosageFreq = dosageFreq;
        this.med = medication;
        this.user = user;
        this.requestType = requestType;
        this.paymentId = paymentId;
    }

    public Integer getId() {
        return requestId;
    }

    public Request setId(Integer id) {
        this.requestId = id;
        return this;
    }

    public Integer getDosageCount() {
        return dosageCount;
    }

    public Request setDosageCount(Integer dosageCount) {
        this.dosageCount = dosageCount;
        return this;
    }

    public Integer getDosageFreq() {
        return dosageFreq;
    }

    public Request setDosageFreq(Integer dosageFreq) {
        this.dosageFreq = dosageFreq;
        return this;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public Request setRequestType(RequestType requestType) {
        this.requestType = requestType;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Request setUser(User user) {
        this.user = user;
        return this;
    }

    public Medication getMed() {
        return med;
    }

    public Request setMed(Medication med) {
        this.med = med;
        return this;
    }

    public Integer getPaymentId() {return paymentId; }

    public Request setPayment(Integer paymentId) {
        this.paymentId = paymentId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(requestId, request.requestId) && Objects.equals(dosageCount, request.dosageCount) && Objects.equals(dosageFreq, request.dosageFreq) && Objects.equals(user, request.user) && Objects.equals(med, request.med) && requestType == request.requestType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, dosageCount, dosageFreq, user, med, requestType);
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestId=" + requestId +
                ", dosageCount=" + dosageCount +
                ", dosageFreq=" + dosageFreq +
                ", user=" + user +
                ", med=" + med +
                ", requestType=" + requestType +
                '}';
    }
}