package com.example.ecommerce_web_shop.service;

import com.example.ecommerce_web_shop.dto.CreateUserDto;
import com.example.ecommerce_web_shop.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserDto> getUsers();

    UserDto findUser(int id);

    UserDto createUser(CreateUserDto createUserDto);

    UserDto updateUser(int id, UserDto userDto);

    void deleteUser(int id);

    void addRoleToUser(int userId, int roleId);
}
