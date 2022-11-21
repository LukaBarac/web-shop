package com.example.ecommerce_web_shop.repositories;


import com.example.ecommerce_web_shop.model.CityReport;
import com.example.ecommerce_web_shop.model.Product;
import com.example.ecommerce_web_shop.model.ProductReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {



    @Query(value = "SELECT * FROM product WHERE date_added BETWEEN DATE_SUB(now(),INTERVAL 7 DAY) AND NOW() ORDER BY date_added DESC LIMIT 5", nativeQuery = true)
    List<Product> findNewProducts();

    @Query(value = "SELECT p.* FROM order_contents oc " +
            "JOIN orders o ON (oc.order_id = o.id) " +
            "JOIN product p ON (p.id = oc.product_id)" +
            "WHERE o.date_created BETWEEN DATE_SUB(now(),INTERVAL 2 WEEK) AND NOW() " +
            "GROUP BY oc.product_id " +
            "ORDER BY sum(oc.quantity) desc LIMIT 10", nativeQuery = true)
    List<Product> findTopProducts();

    @Query(value = "SELECT SUM(oc.quantity) AS amountSold, o.city AS cityName " +
            "FROM orders o " +
            "JOIN order_contents oc ON (o.id = oc.order_id) " +
            "JOIN product p ON (p.id = oc.product_id) " +
            "WHERE oc.product_id = :productId " +
            "GROUP BY o.city",
            nativeQuery = true)
    List<CityReport> findCitiesInfo(int productId);

    @Query(value = "SELECT p.name AS productName, p.price AS productPrice " +
            "FROM product p " +
            "WHERE p.id = :productId",
            nativeQuery = true)
    Optional<ProductReport> findProductNameAndPrice(int productId);
}
