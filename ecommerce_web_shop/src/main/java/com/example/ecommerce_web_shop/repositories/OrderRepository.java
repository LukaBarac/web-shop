package com.example.ecommerce_web_shop.repositories;

import com.example.ecommerce_web_shop.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Page<Order> findAllByUserIdOrderByDateCreatedDesc(int userId, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId " +
            "AND o.dateCreated > COALESCE(:from, '1900-01-01 00:00:00') " +
            "AND o.dateCreated < COALESCE(:to, NOW()) " +
            "ORDER BY o.dateCreated DESC")
    Page<Order> findAllByUserIdAndTimeFrame(int userId, Pageable pageable, LocalDate from, LocalDate to);
}
