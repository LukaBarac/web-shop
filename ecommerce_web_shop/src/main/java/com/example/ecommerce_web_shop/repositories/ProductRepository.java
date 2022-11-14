package com.example.ecommerce_web_shop.repositories;

import com.example.ecommerce_web_shop.dto.ProductDto;
import com.example.ecommerce_web_shop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {



    @Query(value = "SELECT * FROM product WHERE date_added BETWEEN DATE_SUB(now(),INTERVAL 7 DAY) AND NOW() ORDER BY date_added DESC LIMIT 5", nativeQuery = true)
    List<Product> findNewProducts();
}
