package com.example.ecommerce_web_shop.serviceImpl;

import com.example.ecommerce_web_shop.dto.ProductDto;
import com.example.ecommerce_web_shop.exception.NotFoundException;
import com.example.ecommerce_web_shop.mapper.ProductMapper;
import com.example.ecommerce_web_shop.model.Product;
import com.example.ecommerce_web_shop.repositories.ProductRepository;
import com.example.ecommerce_web_shop.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        product = new Product("TV", 20.0, 5);
        productDto = new ProductDto("TV", 20.0, 5);
    }

    @Test
    void shouldGetAllProducts(){
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
        when(productMapper.map(product)).thenReturn(productDto);
        var result = productService.getProducts();
        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(1)).map(product);
        assertEquals("TV", result.get(0).getName());
    }

    @Test
    void shouldReturnEmptyListWhenNoProducts(){
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        var result = productService.getProducts();
        verify(productRepository, times(1)).findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldGetProductById(){
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        when(productMapper.map(product)).thenReturn(productDto);
        var result = productService.getProductById(anyInt());
        verify(productRepository, times(1)).findById(anyInt());
        verify(productMapper, times(1)).map(product);
        assertEquals("TV", result.getName());
        assertEquals(20.0, result.getPrice());
        assertEquals(5, result.getStockAmount());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenNoUserFound(){
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> productService.getProductById(anyInt()));
        verify(productRepository, times(1)).findById(anyInt());
    }
}
