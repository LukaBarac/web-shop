package com.example.ecommerce_web_shop.mapper;

import com.example.ecommerce_web_shop.dto.RoleDto;
import com.example.ecommerce_web_shop.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public Role map(RoleDto roleDto){
        return new Role(roleDto.name());    // jako cudno
    }

    public RoleDto map(Role role){
        return new RoleDto(role.getName());
    }
}
