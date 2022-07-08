package com.revature.model;

import com.revature.model.enums.PayStatus;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;

@Entity(name = "payments")
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Float amount;

    @Enumerated
    @Column(nullable = false)
    private PayStatus status;

    public Integer getId() {
        return id;
    }

    public Payment setId(Integer id) {
        this.id = id;
        return this;
    }

    public Float getAmount() {
        return amount;
    }

    public Payment setAmount(Float amount) {
        this.amount = amount;
        return this;
    }

    public PayStatus getStatus() {
        return status;
    }

    public Payment setStatus(PayStatus status) {
        this.status = status;
        return this;
    }
}
