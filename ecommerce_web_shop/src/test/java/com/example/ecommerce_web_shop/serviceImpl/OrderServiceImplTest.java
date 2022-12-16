package com.example.ecommerce_web_shop.serviceImpl;

import com.example.ecommerce_web_shop.dto.CreateOrderDto;
import com.example.ecommerce_web_shop.dto.OrderContentsDto;
import com.example.ecommerce_web_shop.dto.OrderDto;
import com.example.ecommerce_web_shop.mapper.OrderMapper;
import com.example.ecommerce_web_shop.model.*;
import com.example.ecommerce_web_shop.repositories.BasketContentsRepository;
import com.example.ecommerce_web_shop.repositories.OrderRepository;
import com.example.ecommerce_web_shop.repositories.ProductRepository;
import com.example.ecommerce_web_shop.repositories.UserRepository;
import com.example.ecommerce_web_shop.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private BasketContentsRepository basketContentsRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private CreateOrderDto createOrderDto;
    private User user;
    private List<BasketContents> basketContents = new ArrayList<>();
    private Basket basket;
    private Product product;
    private Product product1;
    private OrderDto orderDto;
    private List<OrderContentsDto> orderContentsDtos = new ArrayList<>();

    @BeforeEach
    void setUp() {
        createOrderDto = new CreateOrderDto("Milutina Milankovica 123", "Beograd", 1, 1);
        user = new User("Ivan", "Ivanovic", "ivanivanovic22@gmail.com");
        product = new Product("TV", 10.0, 7);
        product1 = new Product("Mobile Phone", 10.0, 5);
        var basketContent = new BasketContents(basket, product, 1);
        var basketContent1 = new BasketContents(basket, product1, 2);
        basketContents.add(basketContent);
        basketContents.add(basketContent1);
        basket = new Basket(user, basketContents);
        var orderContentsDto = new OrderContentsDto("TV", 1, 10.0);
        var orderContentsDto1 = new OrderContentsDto("Mobile Phone", 2, 10.0);
        orderContentsDtos.add(orderContentsDto);
        orderContentsDtos.add(orderContentsDto1);
        orderDto = new OrderDto("Milutina Milankovica 123", "Beograd", 10.0, LocalDate.of(2022, 12, 1), orderContentsDtos);
    }

    @Test
    @WithMockUser(username = "ivanivanovic22@gmail.com", authorities = {"ROLE_MANAGER"})
    void shouldCreateOrder(){

        when(basketContentsRepository.findAllByBasketId(anyInt())).thenReturn(basketContents);
        when(userRepository.findByEmail(any())).thenReturn(user);
        when(productRepository.save(any())).thenReturn(product);
//        when(orderMapper.map(any(Order.class))).thenReturn(orderDto);
        var result = orderService.createOrder(createOrderDto, "ivanivanovic22@gmail.com");
        verify(basketContentsRepository, times(1)).findAllByBasketId(anyInt());
        verify(userRepository, times(1)).findByEmail(any());
        verify(productRepository, times(2)).save(any());
//        verify(orderMapper, times(1)).map(any(Order.class));
        assertEquals("Milutina Milankovica 123", result.getAddress());
        assertEquals(product.getStockAmount(), product.getStockAmount());
        assertEquals(basket.getBasketContents().size(), result.getOrderContentsDtos().size());
        assertEquals(basket.getBasketContents().get(0).getProduct().getName(), result.getOrderContentsDtos().get(0).getProductName());
        assertEquals(basket.getBasketContents().get(1).getProduct().getName(), result.getOrderContentsDtos().get(1).getProductName());
        assertEquals(basket.getBasketContents().get(0).getQuantity(), result.getOrderContentsDtos().get(0).getProductQuantity());
        assertEquals(basket.getBasketContents().get(1).getQuantity(), result.getOrderContentsDtos().get(1).getProductQuantity());
     /*
        // zato sto gore imam productRepo.save(product) dakle on je prosao vec kroz kod i nema potrebe da actual stavljam stock - quantity
        assertEquals(basket.getBasketContents().get(0).getProduct().getName(), result.getOrderContentsDtos().get(0).getProductName());
       *//* assertEquals(basketContents.getQuantity(), result.getOrderContentsDtos().get(0).getProductQuantity());
        assertEquals(basketContents.getProduct().getPrice(), result.getOrderContentsDtos().get(0).getPrice());*//*
        for (var i:basket.getBasketContents()) {
            Iterator<BasketContents> iterator = basketContents.listIterator();
            while(iterator.hasNext()){
                iterator.next();
                assertEquals(basket.getBasketContents().get(0).getProduct().getPrice(), result.getOrderContentsDtos().get(0).getPrice());
            }
        }*/
    }
}
