package com.example.ecommerce_web_shop.dto;

import com.example.ecommerce_web_shop.enums.ProductStockStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopProductsDto {

    private String name;
    private double price;
    private ProductStockStatus availability;
}
