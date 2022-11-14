package com.example.ecommerce_web_shop.service.impl;

import com.example.ecommerce_web_shop.dto.RoleDto;
import com.example.ecommerce_web_shop.mapper.RoleMapper;
import com.example.ecommerce_web_shop.model.Role;
import com.example.ecommerce_web_shop.repositories.RoleRepository;
import com.example.ecommerce_web_shop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public RoleDto saveRole(RoleDto roleDto) {

        var role = roleRepository.save(roleMapper.map(roleDto));
        return roleMapper.map(role);
    }
}
