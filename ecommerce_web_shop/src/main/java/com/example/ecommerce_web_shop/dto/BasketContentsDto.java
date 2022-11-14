package com.example.ecommerce_web_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BasketContentsDto {

    private int basketId;
    private int productId;
//     private String productName;
//     private double productPrice;
    private int quantity;

/*    public BasketContentsDto(List<BasketContentsDto> baskets) {

    }*/
}
