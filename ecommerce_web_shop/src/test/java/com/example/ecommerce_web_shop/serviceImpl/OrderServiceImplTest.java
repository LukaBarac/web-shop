package com.example.ecommerce_web_shop.serviceImpl;

import com.example.ecommerce_web_shop.dto.CreateOrderDto;
import com.example.ecommerce_web_shop.dto.OrderContentsDto;
import com.example.ecommerce_web_shop.dto.OrderDto;
import com.example.ecommerce_web_shop.filter.CustomAuthenticationFilter;
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
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import java.time.LocalDate;
import java.util.Collections;
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
    private BasketContents basketContents;
    private Basket basket;
    private Product product;
    private OrderDto orderDto;
    private List<OrderContentsDto> orderContentsDtos;

    @BeforeEach
    void setUp() {
        createOrderDto = new CreateOrderDto("Milutina Milankovica 123", "Beograd", 1, 1);
        user = new User("Ivan", "Ivanovic", "ivanivanovic22@gmail.com");
        product = new Product("TV", 10, 5);
        basketContents = new BasketContents(basket, product, 4);
        orderDto = new OrderDto("Milutina Milankovica 123", "Beograd", 123, LocalDate.of(2022, 12, 1), orderContentsDtos);

    }

    @Test
    @WithMockUser(username = "ivanivanovic22@gmail.com", authorities = {"ROLE_MANAGER"})
    void shouldCreateOrder(){
       /* try(MockedStatic<SecurityContextHolder> mocked = Mockito.mockStatic(SecurityContextHolder.class)){
            *//*mocked.when(() -> SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("ivanivanovic22@gmail.com");*//*
            mocked.when(SecurityContextHolder::getContext).thenReturn(user);
            mocked.when(() -> )
        }*/
       /* Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        try(MockedStatic<SecurityContextHolder> mocked = Mockito.mockStatic(SecurityContextHolder.class)){
            mocked.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            mocked.when(() -> SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
            mocked.when(() -> SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("ivanivanovic22@gmail.com");
        }
//        when(securityContext.getAuthentication()).thenReturn(authentication); prolazi i bez ovoga
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication().getName()).thenReturn("ivanivanovic22@gmail.com");
*/

        when(basketContentsRepository.findAllByBasketId(anyInt())).thenReturn(Collections.singletonList(basketContents));
        when(userRepository.findByEmail(any())).thenReturn(user);
        when(productRepository.save(any())).thenReturn(product);
        when(orderMapper.map(any(Order.class))).thenReturn(orderDto);
        var result = orderService.createOrder(createOrderDto, "ivanivanovic22@gmail.com");
        verify(basketContentsRepository, times(1)).findAllByBasketId(anyInt());
        verify(userRepository, times(1)).findByEmail(any());
        verify(productRepository, times(1)).save(any());
        verify(orderMapper, times(1)).map(any(Order.class));
        assertEquals("Milutina Milankovica 123", result.getAddress());
        assertEquals(product.getStockAmount(), product.getStockAmount() - basketContents.getQuantity());
    }
}


// treba da proverim da li u mom orderu, u basketu sa odredjenim baskedId-jem, imam vise produkata i koji su i sve