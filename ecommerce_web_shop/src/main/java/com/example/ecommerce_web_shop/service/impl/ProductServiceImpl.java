package com.example.ecommerce_web_shop.service.impl;
import com.example.ecommerce_web_shop.dto.ProductDto;
import com.example.ecommerce_web_shop.exception.NotFoundException;
import com.example.ecommerce_web_shop.mapper.ProductMapper;
import com.example.ecommerce_web_shop.model.Product;
import com.example.ecommerce_web_shop.repositories.ProductRepository;
import com.example.ecommerce_web_shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream().map(productMapper::map).collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(int id/*, ProductDto productDto*/) {
        var savedEntity = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("product does not exist"));
        return productMapper.map(savedEntity);
    }

    @Override
    public List<ProductDto> findNewProducts(){
        return productRepository.findNewProducts().stream().map(productMapper::map).collect(Collectors.toList());
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        var savedEntity = productRepository.save(productMapper.map(productDto));
        return productMapper.map(savedEntity);
    }

    @Override
    public List<ProductDto> addProducts(List<ProductDto> products) {
        return null;  //poradi na ovome
    }

    @Override
    public void deleteProduct(int productId) {
        this.productRepository.deleteById(productId);
    }
}
