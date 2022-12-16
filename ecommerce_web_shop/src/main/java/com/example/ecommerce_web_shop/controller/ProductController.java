package com.example.ecommerce_web_shop.controller;

import com.example.ecommerce_web_shop.dto.ProductDto;
import com.example.ecommerce_web_shop.dto.ReportDto;
import com.example.ecommerce_web_shop.dto.TopProductsDto;
import com.example.ecommerce_web_shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable int id/*, ProductDto productDto*/){
        return ResponseEntity.ok(productService.getProductById(id/*, productDto*/));
    }

    @GetMapping("newProducts")
    public ResponseEntity<List<ProductDto>> getNewProducts(){
        return ResponseEntity.ok(productService.findNewProducts());
    }

    @GetMapping("topProducts")
    public ResponseEntity<List<TopProductsDto>> getTopProducts(){
        return ResponseEntity.ok(productService.getTopProducts());
    }

    @GetMapping("report/{productId}")
    public ResponseEntity<ReportDto> findReport(@PathVariable int productId){
        return ResponseEntity.ok(productService.getReport(productId));
    }

    @PostMapping("")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        return new ResponseEntity<>(productService.createProduct(productDto), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable(name = "id") int productId){
        productService.deleteProduct(productId);
    }

    //4. ubaci vise razlicitih produkta odjednom  NIJE URADJENO
    public ResponseEntity<List<ProductDto>> createProduct(@RequestBody List<ProductDto> products){
            return new ResponseEntity<>(productService.addProducts(products), HttpStatus.CREATED);
    }
}
