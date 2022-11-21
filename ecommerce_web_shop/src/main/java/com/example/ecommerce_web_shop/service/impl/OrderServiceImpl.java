package com.example.ecommerce_web_shop.service.impl;

import com.example.ecommerce_web_shop.dto.CreateOrderDto;
import com.example.ecommerce_web_shop.dto.OrderDto;
import com.example.ecommerce_web_shop.exception.BadRequestException;
import com.example.ecommerce_web_shop.mapper.OrderMapper;
import com.example.ecommerce_web_shop.model.Order;
import com.example.ecommerce_web_shop.model.OrderContents;
import com.example.ecommerce_web_shop.model.Product;
import com.example.ecommerce_web_shop.repositories.*;
import com.example.ecommerce_web_shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderContentsRepository orderContentsRepository;

    @Autowired
    private BasketContentsRepository basketContentsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Page<OrderDto> getAllOrders(int userId, Pageable pageable) {
        return orderRepository.findAllByUserIdOrderByDateCreatedDesc(userId, pageable).map(orderMapper::map);
    }

    @Override
    public Page<OrderDto> getFilteredOrders(int userId, Pageable pageable, Date from, Date to) {
        return orderRepository.findAllByUserIdAndTimeFrame(userId, pageable, from, to).map(orderMapper::map);
    }

  /*  @Override
    public Page<OrderDto> getAllOrders(Pageable pageable) {
        var user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return orderRepository.findAllByUserIdOrderByDateCreatedDesc(user.getId(), pageable).map(orderMapper::map);
    }
*/
    @Override
    public OrderDto createOrder(CreateOrderDto createOrderDto) {

        var basketContents = basketContentsRepository.findAllByBasketId(createOrderDto.basketId());
        var user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        var totalPrice = 0.0;

        Order order = new Order(createOrderDto.address(), createOrderDto.city(), totalPrice, user);
        var sum = 0.0;
        List<OrderContents> orderContentsList = new ArrayList<>();
        for (var basketContent: basketContents) {
            OrderContents orderContents = new OrderContents();
            var stockAmount = basketContent.getProduct().getStockAmount(); // samo radi lakse
            var quantity = basketContent.getQuantity();                    // preglednosti u if-u
            Product product = basketContent.getProduct();
            if (stockAmount >= quantity){
                orderContents.setQuantity(basketContent.getQuantity());
                product.setStockAmount(product.getStockAmount() - basketContent.getQuantity());
                productRepository.save(product);
            } else {
                throw new BadRequestException("There is not a sufficient amount of product in stock!");
            }
            orderContents.setProduct(product);
            orderContents.setPrice(basketContent.getProduct().getPrice());
            orderContents.setName(basketContent.getProduct().getName());
            sum = sum + (orderContents.getPrice() * orderContents.getQuantity());
            orderContents.setOrder(order);
            orderContentsList.add(orderContents);
        }

        order.setTotalPrice(sum);
        order.setOrderContents(orderContentsList);
        orderRepository.save(order);
        /*orderContentsRepository.saveAll(orderContentsList); sad kada map ima samo order kao entry, ovo nema potrebe*/

        /*basketService.emptyBasket(createOrderDto.basketId());    da ne popunjavam svaki put dok testiram*/

        return orderMapper.map(order);
    }

    //   public OrderContents convert(BasketContents basketContents){return null;}
        // stavi if odvojeno = vidi sum sta kako
    //
    //





    // treba izbaciti setTotalPrice iz petlje skroz, dakle da taj sum ide kroz iteracije i kupi total price
    // ali da tek da izvan petlje to setujem
    // total price zavisi i od quantitija, dakle sum = price * quantity

/*
    @Override
    public Page<OrderDto> getAllOrders(String userName, Pageable pageable) {
        var user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return orderRepository.findAllByUserEmailOrderByDateCreatedDesc(user.getEmail(), pageable)
                .stream().map(orderMapper::map).collect(Collectors.toList());
        //return basketRepository.findAll().stream().map(basketMapper::map).collect(Collectors.toList());
    }
*/

}
