package com.example.ecommerce_web_shop.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class BasketContentsPK implements Serializable {

    private int basketId;
    private int productId;
}
