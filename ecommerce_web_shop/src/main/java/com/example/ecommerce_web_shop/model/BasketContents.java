package com.example.ecommerce_web_shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
/*@Table(name = "basket_contents")*/
//@ToString         // RADI I SA I BEZ OVOGA
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
