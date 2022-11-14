package com.example.ecommerce_web_shop.controller;

import com.example.ecommerce_web_shop.dto.RoleDto;
import com.example.ecommerce_web_shop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role/")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("")
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto){
        return new ResponseEntity<>(roleService.saveRole(roleDto), HttpStatus.CREATED);
    }
}
