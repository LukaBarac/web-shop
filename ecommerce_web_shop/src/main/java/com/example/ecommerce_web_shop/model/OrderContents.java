package com.example.ecommerce_web_shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_contents")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderContents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String name;
    private double price;
    private int quantity;

    public OrderContents(Order order, Product product, String name, double price, int quantity) {
        this.order = order;
        this.product = product;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
