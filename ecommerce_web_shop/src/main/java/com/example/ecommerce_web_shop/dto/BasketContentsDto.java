package com.example.ecommerce_web_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BasketContentsDto {

    private int basketId;
    private int productId;
    private int quantity;
}
