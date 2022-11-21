package com.example.ecommerce_web_shop.service;

import com.example.ecommerce_web_shop.dto.CreateOrderDto;
import com.example.ecommerce_web_shop.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface OrderService {

    Page<OrderDto> getAllOrders(int userId, Pageable pageable);

    Page<OrderDto> getFilteredOrders(int userId, Pageable pageable, Date from, Date to);

    /*Page<OrderDto> getAllOrders(Pageable pageable);*/

    OrderDto createOrder(CreateOrderDto createOrderDto);

}
