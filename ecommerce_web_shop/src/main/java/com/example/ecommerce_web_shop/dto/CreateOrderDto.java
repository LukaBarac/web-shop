package com.example.ecommerce_web_shop.dto;

public record CreateOrderDto(String address, String city, int basketId, int userId) {
}
