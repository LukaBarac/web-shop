package com.example.ecommerce_web_shop.mapper;

import com.example.ecommerce_web_shop.dto.CreateUserDto;
import com.example.ecommerce_web_shop.dto.UserDto;
import com.example.ecommerce_web_shop.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto map(User user){
        return new UserDto(user.getFirstName(),
                user.getLastName(),
                user.getEmail());
    }

    public User map(UserDto userDto){
        return new User(userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail());
    }


    public User map(CreateUserDto createUserDto){
        return new User(createUserDto.firstName(),
                createUserDto.lastName(),
                createUserDto.email(),
                createUserDto.password()); // ja ovde samo mapiram u Usera, a roleName cu da hvatam iz roleRepository u servisu
    }
}
