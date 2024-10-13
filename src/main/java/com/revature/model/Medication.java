package com.revature.model;

import com.revature.model.enums.Status;
import com.revature.model.enums.Type;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "medications")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int medId;

    @Column
    private String name;
    @Column
    private int stock;
    @Column
    private Double price;
    @Enumerated
    private Type type;
    @Enumerated
    private Status status;

    public Medication(){

    }

    public Medication(int id, String name, int stock, Double price, Type type, Status status) {
        this.medId = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.type = type;
        this.status = status;
    }

    public int getId() {
        return medId;
    }

    public Medication setId(int id) {
        this.medId = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Medication setName(String name) {
        this.name = name;
        return this;
    }

    public int getStock() {
        return stock;
    }

    public Medication setStock(int stock) {
        this.stock = stock;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Medication setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Type getType() {
        return type;
    }

    public Medication setType(Type type) {
        this.type = type;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Medication setStatus(Status status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medication that = (Medication) o;
        return medId == that.medId && stock == that.stock && Objects.equals(name, that.name) && Objects.equals(price, that.price) && type == that.type && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(medId, name, stock, price, type, status);
    }

    @Override
    public String toString() {
        return "Medication{" +
                "medId=" + medId +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}
