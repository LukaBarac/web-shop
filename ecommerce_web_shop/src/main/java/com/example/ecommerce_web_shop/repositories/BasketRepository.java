package com.example.ecommerce_web_shop.repositories;

import com.example.ecommerce_web_shop.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
}
