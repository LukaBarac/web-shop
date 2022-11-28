package com.example.ecommerce_web_shop.service;

import com.example.ecommerce_web_shop.dto.ProductDto;
import com.example.ecommerce_web_shop.dto.ReportDto;
import com.example.ecommerce_web_shop.dto.TopProductsDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getProducts();
    ProductDto getProductById(int id);

    List<ProductDto> findNewProducts();

    ProductDto createProduct(ProductDto productDto);

    List<ProductDto> addProducts(List<ProductDto> products);

    void deleteProduct(int productId);

    List<TopProductsDto> getTopProducts();

    ReportDto getReport(int productId);
}
