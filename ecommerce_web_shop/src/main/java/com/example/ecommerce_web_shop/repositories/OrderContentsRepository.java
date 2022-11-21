package com.example.ecommerce_web_shop.repositories;

import com.example.ecommerce_web_shop.model.OrderContents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderContentsRepository extends JpaRepository<OrderContents, Integer> {
}
