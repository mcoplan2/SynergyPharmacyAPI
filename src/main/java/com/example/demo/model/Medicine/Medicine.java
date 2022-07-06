package com.example.demo.model.Medicine;

import javax.persistence.*;

@Entity
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer MedId;

    @Column
    private String name;

    @Column
    private Integer stock;

    @Column
    private Double price;

    @Column
    private Type type;

    @Enumerated
    private Status status;
}
