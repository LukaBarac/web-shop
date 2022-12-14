package com.example.ecommerce_web_shop.mapper;

import com.example.ecommerce_web_shop.dto.CreateUserDto;
import com.example.ecommerce_web_shop.dto.RoleDto;
import com.example.ecommerce_web_shop.dto.UserDto;
import com.example.ecommerce_web_shop.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto map(User user){
        RoleDto roleDto = new RoleDto(user.getRole().getName());
        return new UserDto(user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                roleDto);
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

    public User mapUpdatedUser(UserDto userDto, User user){     // ako dodeljujem
                                                                // promenljivoj u servisu onda mora tip User
/*
        if(userDto.getFirstName() != null &&
           userDto.getLastName() != null &&
            userDto.getEmail() != null) {
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());    //ovo ima smisla da je PUT
            user.setEmail(userDto.getEmail());
        }*/

        if(userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }
        if(userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());  //vidi da li da ostane PUT ili da stavim PATCH
        }
        if(userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }

        return user;
    }
}
