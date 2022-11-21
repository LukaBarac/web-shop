package com.example.ecommerce_web_shop.controller;

import com.example.ecommerce_web_shop.dto.CreateOrderDto;
import com.example.ecommerce_web_shop.dto.OrderDto;
import com.example.ecommerce_web_shop.dto.TopProductsDto;
import com.example.ecommerce_web_shop.model.Order;
import com.example.ecommerce_web_shop.service.OrderService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("{userId}")
    public ResponseEntity<Page<OrderDto>> getAllOrders(@PathVariable int userId, Pageable pageable){
        return ResponseEntity.ok(orderService.getAllOrders(userId, pageable));
    }

    @GetMapping("dateFiltered/{userId}")
    public ResponseEntity<Page<OrderDto>> getFilteredOrders(@PathVariable int userId, Pageable pageable,
                                                            @RequestParam(value = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date from,
                                                            @RequestParam(value = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date to){
        return ResponseEntity.ok(orderService.getFilteredOrders(userId, pageable, from, to));
    }


    @PostMapping("")
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderDto createOrderDto){
        return new ResponseEntity<>(orderService.createOrder(createOrderDto), HttpStatus.CREATED);
    }




}
