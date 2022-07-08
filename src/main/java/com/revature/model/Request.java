package com.revature.model;

import com.revature.model.enums.RequestType;
import com.revature.model.enums.Type;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "requests")
public class Request implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Integer dosageCount; // amount of pills the user will receive on their prescription.
    @Column(nullable = false)
    private Integer dosageFreq; // number of
    // times per day they need to take this pill

    @ManyToOne
    @Column(nullable = false)
    private User creator;

    @Column(nullable = false)
    //@ManytoOne(cascade = CascadeType.PERSIST)
    private Medicine med;

    @Enumerated
    private RequestType requestType = RequestType.OPEN;


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

    public User getCreator() {
        return creator;
    }

    public Request setCreator(User creator) {
        this.creator = creator;
        return this;
    }

    public Medicine getMed() {
        return med;
    }

    public Request setMed(Medicine med) {
        this.med = med;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id) && Objects.equals(dosageCount, request.dosageCount) && Objects.equals(dosageFreq, request.dosageFreq) && Objects.equals(creator, request.creator) && Objects.equals(med, request.med) && requestType == request.requestType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dosageCount, dosageFreq, creator, med, requestType);
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", dosageCount=" + dosageCount +
                ", dosageFreq=" + dosageFreq +
                ", creator=" + creator +
                ", med=" + med +
                ", requestType=" + requestType +
                '}';
    }
}