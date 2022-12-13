package com.example.ecommerce_web_shop.integration;

import com.example.ecommerce_web_shop.dto.CreateOrderDto;
import com.example.ecommerce_web_shop.repositories.BasketContentsRepository;
import com.example.ecommerce_web_shop.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:data.sql"}, executionPhase = BEFORE_TEST_METHOD)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BasketContentsRepository basketContentsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    private CreateOrderDto createOrderDto;

    @BeforeEach
    void setUp() {
        createOrderDto = new CreateOrderDto("Milutina Milankovica 23", "Beograd", 1, 1);

    }

    @Test
    @WithUserDetails(value = "ivanivanovic231@hotmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void shouldCreateOrder() throws Exception {

        System.out.println("ovdeeeeee " + userRepository.findAll());

        var content = objectMapper.writeValueAsString(createOrderDto);

        mockMvc.perform(post("/order/")             // ovo je mockMvc request builder import
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(jsonPath("$.address").value("Milutina Milankovica 23")); // mockmvc result matchers

    }
}
