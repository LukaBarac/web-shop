package com.example.ecommerce_web_shop.repositories;

import com.example.ecommerce_web_shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}
