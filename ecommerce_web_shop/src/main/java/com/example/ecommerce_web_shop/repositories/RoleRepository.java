package com.example.ecommerce_web_shop.repositories;

import com.example.ecommerce_web_shop.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name); // mora zbog orElseThrow!!!!!!!
}
