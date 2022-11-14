package com.example.ecommerce_web_shop.dto;

public record CreateUserDto(String firstName,
                            String lastName,
                            String email,
                            String password,
                            String roleName) {
}
