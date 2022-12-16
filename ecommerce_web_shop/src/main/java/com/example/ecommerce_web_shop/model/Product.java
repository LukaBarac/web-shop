package com.example.ecommerce_web_shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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

    @Column(name = "product_name")
    private String name;
    private double price;
    private int stockAmount;

    @CreationTimestamp
    @Column(name = "date_added")
    private LocalDate dateAdded;

    public Product(String name, double price, int stockAmount){
        this.name = name;
        this.price = price;         // ovaj konstruktor je za drugi map u product mapperu
        this.stockAmount = stockAmount;
    }
}
