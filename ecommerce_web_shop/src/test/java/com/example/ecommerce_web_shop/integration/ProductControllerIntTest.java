package com.example.ecommerce_web_shop.integration;

import com.example.ecommerce_web_shop.dto.ProductDto;
import com.example.ecommerce_web_shop.model.Product;
import com.example.ecommerce_web_shop.repositories.ProductRepository;
import com.example.ecommerce_web_shop.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    private Product product;

    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        product = new Product("TV", 20.0, 5);
        productDto = new ProductDto("TV", 20.0, 5);
    }

    @Test
        //@Sql(scripts = {"classpath:data.sql"}, executionPhase = BEFORE_TEST_METHOD)
    void shouldCreateProduct() throws Exception {

        productService.createProduct(productDto);

        var content = objectMapper.writeValueAsString(productDto);

        mockMvc.perform(post("/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(jsonPath("$[0].name").value("TV"));
        System.out.println("radi");
    }
    @Test
    //@Sql(scripts = {"classpath:data.sql"}, executionPhase = BEFORE_TEST_METHOD)
    void shouldGetAllProducts() throws Exception {
        productService.getProducts();
        var content = objectMapper.writeValueAsString(productDto);
        mockMvc.perform(
                        get("/users/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(jsonPath("$[0].firstName").value("Ivan"));


    }
  /*  @Test
    void context(){}*/

}


