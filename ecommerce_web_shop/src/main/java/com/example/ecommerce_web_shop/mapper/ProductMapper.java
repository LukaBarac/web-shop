package com.example.ecommerce_web_shop.mapper;

import com.example.ecommerce_web_shop.dto.ProductDto;
import com.example.ecommerce_web_shop.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto
    map(Product product){
        return new ProductDto(product.getName(),
                product.getPrice(),
                product.getStockAmount());
    }

    public Product map(ProductDto productDto){
        return new Product(productDto.getName(),
                productDto.getPrice(),
                productDto.getStockAmount());
    }
}
