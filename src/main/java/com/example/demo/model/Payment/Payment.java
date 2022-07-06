package com.example.demo.model.Payment;

import javax.persistence.*;

@Entity
public class Payment{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String reqId;

    @Column
    private Double balance;

    @Column
    private Boolean outstanding;

}
