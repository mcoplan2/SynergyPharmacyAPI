package com.example.demo.model.Request;

import com.example.demo.model.User.User;

import javax.persistence.*;

@Entity(name="Request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    @Column
    private String userId;

    @Column
    private String MedId;

    @Column
    private Integer DosageCount;

    @Column
    private Integer DosageFreq;

    @Column
    private Boolean Fulfilled;

}
