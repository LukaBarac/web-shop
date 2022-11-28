package com.example.ecommerce_web_shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private double price;
    private int stockAmount;

    @CreationTimestamp
/*
    @Temporal(TemporalType.TIMESTAMP)   // vidi chat sa nikolom
*/
    private LocalDate dateAdded;

    public Product(String name, double price, int stockAmount){
        this.name = name;
        this.price = price;         // ovaj konstruktor je za drugi map u product mapperu
        this.stockAmount = stockAmount;
    }

 /*   public Product(int id, String name, double price, int stockAmount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockAmount = stockAmount;
    }*/
}
