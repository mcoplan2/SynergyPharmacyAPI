package com.revature.model;

import com.revature.model.enums.RequestType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "requests")
public class Request implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer dosageCount; // amount of pills the user will receive on their prescription.
    private Integer dosageFreq; // number of
    // times per day they need to take this pill

    @Enumerated
    private RequestType requestType;

    /*
    @ManytoOne(cascade = CascadeType.PERSIST)
    private User creator;

    @ManytoOne(cascade = CascadeType.PERSIST)
    private Medicine med;


    private Type type; // if we decide to use Type Enums for medication


     */
    public Request() {
    }

    public Request(Integer id, Integer dosageCount, Integer dosageFreq, RequestType requestType) {
        this.id = id;
        this.dosageCount = dosageCount;
        this.dosageFreq = dosageFreq;
        this.requestType = requestType;
    }

    public Integer getId() {
        return id;
    }

    public Request setId(Integer id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id) && Objects.equals(dosageCount, request.dosageCount) && Objects.equals(dosageFreq, request.dosageFreq) && requestType == request.requestType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dosageCount, dosageFreq, requestType);
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", dosageCount=" + dosageCount +
                ", dosageFreq=" + dosageFreq +
                ", requestType=" + requestType +
                '}';
    }
}