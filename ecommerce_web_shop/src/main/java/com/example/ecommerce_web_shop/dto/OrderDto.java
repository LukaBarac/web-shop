package com.example.ecommerce_web_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String address;
    private String city;
    private double totalPrice;
    private LocalDate dateCreated;
    private List<OrderContentsDto> orderContentsDtos;
}
