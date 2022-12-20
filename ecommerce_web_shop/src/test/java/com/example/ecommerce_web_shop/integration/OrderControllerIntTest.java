package com.example.ecommerce_web_shop.integration;

import com.example.ecommerce_web_shop.dto.CreateOrderDto;
import com.example.ecommerce_web_shop.exception.NotFoundException;
import com.example.ecommerce_web_shop.model.Basket;
import com.example.ecommerce_web_shop.model.BasketContents;
import com.example.ecommerce_web_shop.repositories.BasketContentsRepository;
import com.example.ecommerce_web_shop.repositories.BasketRepository;
import com.example.ecommerce_web_shop.repositories.ProductRepository;
import com.example.ecommerce_web_shop.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BasketContentsRepository basketContentsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    private CreateOrderDto createOrderDto;


    private BasketContents basketContents;

    @BeforeEach
    void setUp() {
        createOrderDto = new CreateOrderDto("Milutina Milankovica 23", "Beograd", 1, 1);

    }

    @Test
    @Order(1)
    @Sql(scripts = {"classpath:data.sql"}, executionPhase = BEFORE_TEST_METHOD)
    @WithUserDetails(value = "ivanivanovic231@hotmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void shouldCreateOrder() throws Exception {

        System.out.println("OVDEEEEEEEEEEEEEEEEEEEE " + userRepository.findAll());

        var content = objectMapper.writeValueAsString(createOrderDto);

        mockMvc.perform(post("/order/")             // ovo je mockMvc request builder import
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(jsonPath("$.address").value("Milutina Milankovica 23")) // mockmvc result matchers
                .andExpect(jsonPath("$.orderContentsDtos[0].productName").value("Mobile Phone"))
                .andExpect(jsonPath("$.orderContentsDtos[0].productQuantity").value(3))
                .andExpect(jsonPath("$.orderContentsDtos[1].productName").value("TV"))
                .andExpect(jsonPath("$.orderContentsDtos[1].productQuantity").value(2))
                .andExpect(jsonPath("$.totalPrice").value(820));


        var basket = basketRepository.findById(1).orElseThrow(() -> new NotFoundException("nema"));
        var product1 = productRepository.findById(1).orElseThrow(() -> new NotFoundException("nema"));
        var product2 = productRepository.findById(2).orElseThrow(() -> new NotFoundException("nema"));

//        assertTrue(basketContentsRepository.findAllByBasketId(1).isEmpty()); //ovako ili preko basket objekta?
        assertTrue(basket.getBasketContents().isEmpty()); // isto kao ovo gore

        assertEquals(product1.getStockAmount(), 2); // ovo ne moze direktno preko productRepo?
        assertEquals(product2.getStockAmount(), 5);

        System.out.println("posle testa " + userRepository.findAll());
    }

    @Test
    @Order(3)
    @Sql(scripts = {"classpath:dataException.sql"}, executionPhase = BEFORE_TEST_METHOD)
    @WithUserDetails(value = "ivanivanovic231@hotmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void shouldThrowForbiddenExceptionWhenCreateOrder/*AndCheckIfRollbackWorks*/() throws Exception {

        var content = objectMapper.writeValueAsString(createOrderDto);

        mockMvc.perform(post("/order/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is4xxClientError());

        var product1 = productRepository.findById(1).orElseThrow(() -> new NotFoundException("nema"));
        var product2 = productRepository.findById(2).orElseThrow(() -> new NotFoundException("nema"));

        assertEquals(product1.getStockAmount(), 5);
        assertEquals(product2.getStockAmount(), 1);
    }

    @Test
    @Order(2)
//    @Sql(scripts = {"classpath:data.sql"}, executionPhase = BEFORE_TEST_METHOD)
    @WithUserDetails(value = "ivanivanovic231@hotmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void shouldGetAllOrdersViaOrderId() throws Exception {

        var content = objectMapper.writeValueAsString(createOrderDto);

        mockMvc.perform(get("/order/", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(jsonPath("$.address").value("Milutina Milankovica 23"))
                .andExpect(jsonPath("$.orderContentsDtos[0].productName").value("Mobile Phone"));

    }
}
