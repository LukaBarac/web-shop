package com.example.ecommerce_web_shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BasketContents {

    @EmbeddedId
    private BasketContentsPK id;

    @ManyToOne
    @MapsId("basketId")
    private Basket basket;

    @ManyToOne
    @MapsId("productId")
    private Product product;

    private int quantity;

    public BasketContents(Basket basket, Product product, int quantity) {
        this.basket = basket;
        this.product = product;
        this.quantity = quantity;
    }


}
