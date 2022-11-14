package com.example.ecommerce_web_shop.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.ecommerce_web_shop.dto.CreateUserDto;
import com.example.ecommerce_web_shop.dto.UserDto;
import com.example.ecommerce_web_shop.model.User;
import com.example.ecommerce_web_shop.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/users/")
public class UserController {

    @Autowired
    private UserService userService;    //zasto koristim interfejs?!?!?!

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
