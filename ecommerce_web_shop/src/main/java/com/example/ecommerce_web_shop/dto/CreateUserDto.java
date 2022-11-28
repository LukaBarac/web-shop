package com.example.ecommerce_web_shop.dto;

import javax.validation.constraints.Email;

public record CreateUserDto(String firstName,
                            String lastName,
                            @Email
                            String email,
                            String password,
                            String roleName) {
}
