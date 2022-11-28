package com.example.ecommerce_web_shop.controller;


import com.example.ecommerce_web_shop.dto.CreateUserDto;
import com.example.ecommerce_web_shop.dto.UserDto;
import com.example.ecommerce_web_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<List<UserDto>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable int id){
        return ResponseEntity.ok(userService.findUser(id));
    }

    @PostMapping("")
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserDto createUserDto){
        return new ResponseEntity<>(this.userService.createUser(createUserDto), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int id, @RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable int id){
        userService.deleteUser(id);
    }

    @PatchMapping("{userId}/{roleId}")
    public void addRoleToUser(@PathVariable int userId, @PathVariable int roleId){userService.addRoleToUser(userId, roleId);}

}
