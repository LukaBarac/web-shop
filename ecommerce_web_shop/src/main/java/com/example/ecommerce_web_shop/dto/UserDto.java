package com.example.ecommerce_web_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    private String firstName;
    private String lastName;
    private String email;
  /*  private RoleDto role;*/
}
