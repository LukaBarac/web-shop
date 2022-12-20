package com.example.ecommerce_web_shop.service.impl;

import com.example.ecommerce_web_shop.dto.CreateOrderDto;
import com.example.ecommerce_web_shop.dto.OrderDto;
import com.example.ecommerce_web_shop.exception.BadRequestException;
import com.example.ecommerce_web_shop.mapper.OrderMapper;
import com.example.ecommerce_web_shop.model.BasketContents;
import com.example.ecommerce_web_shop.model.Order;
import com.example.ecommerce_web_shop.model.OrderContents;
import com.example.ecommerce_web_shop.model.Product;
import com.example.ecommerce_web_shop.repositories.*;
import com.example.ecommerce_web_shop.service.BasketService;
import com.example.ecommerce_web_shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BasketContentsRepository basketContentsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BasketService basketService;

    @Override
    public Page<OrderDto> getAllOrders(int userId, Pageable pageable) {
        return orderRepository.findAllByUserIdOrderByDateCreatedDesc(userId, pageable).map(OrderMapper::map);
    }

    @Override
    public Page<OrderDto> getFilteredOrders(int userId, Pageable pageable, Date from, Date to) {
        return orderRepository.findAllByUserIdAndTimeFrame(userId, pageable, from, to).map(OrderMapper::map);
    }

    /*  @Override
      public Page<OrderDto> getAllOrders(Pageable pageable) {
          var user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
          return orderRepository.findAllByUserIdOrderByDateCreatedDesc(user.getId(), pageable).map(orderMapper::map);
      }
  */
    @Override
    @Transactional
    public OrderDto createOrder(CreateOrderDto createOrderDto, String email) {
        var basketContents = basketContentsRepository.findAllByBasketId(createOrderDto.basketId());
        var user = userRepository.findByEmail(email);
        var totalPrice = 0.0;

        Order order = new Order(createOrderDto.address(), createOrderDto.city(), totalPrice, user);

        var orderContentsList = populateOrderContentsList(basketContents, order); // da li je bolje da imenujem drugacije, iako bi bilo ok da stoji OrderContentsList dvaput jer nije isti scope

        order.setOrderContents(orderContentsList);
        orderRepository.save(order);

        basketService.emptyBasket(createOrderDto.basketId());

        return OrderMapper.map(order);
    }

    public List<OrderContents> populateOrderContentsList(List<BasketContents> basketContents, Order order) {
        double sum = 0.0;
        List<OrderContents> orderContentsList = new ArrayList<>();
        for (var basketContent : basketContents) {
            var orderContents = convertBasketContentsToOrderContents(basketContent, order);
            sum = sum + (orderContents.getPrice() * orderContents.getQuantity());
            orderContents.setOrder(order);
            orderContentsList.add(orderContents);
        }
        order.setTotalPrice(sum);
        return orderContentsList;
    }

    public OrderContents convertBasketContentsToOrderContents(BasketContents basketContents, Order order){
        var product = basketContents.getProduct();
        var quantity = basketContents.getQuantity();
        checkProductAvailabilityAndUpdate(product, quantity);
        return new OrderContents(order, product, product.getName(), product.getPrice(), quantity);
    }

    public void checkProductAvailabilityAndUpdate(Product product, int quantity){
        var stockAmount = product.getStockAmount();
        if (stockAmount >= quantity) {
            product.setStockAmount(stockAmount - quantity);
            productRepository.save(product);
        } else {
            throw new BadRequestException("There is not a sufficient amount of product in stock!");
        }
    }
}