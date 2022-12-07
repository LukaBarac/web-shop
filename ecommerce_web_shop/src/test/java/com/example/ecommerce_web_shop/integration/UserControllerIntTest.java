package com.example.ecommerce_web_shop.integration;

import com.example.ecommerce_web_shop.dto.CreateUserDto;
import com.example.ecommerce_web_shop.dto.RoleDto;
import com.example.ecommerce_web_shop.dto.UserDto;
import com.example.ecommerce_web_shop.model.User;
import com.example.ecommerce_web_shop.repositories.RoleRepository;
import com.example.ecommerce_web_shop.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = true)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    private CreateUserDto createUserDto;

    private UserDto userDto;

    //private User user;

    private final static String FIRST_NAME = "Ivan";
    private final static String EMAIL = "ivanivanovic22@gmail.com";

    @BeforeEach
    void setUp() {
      //  roleRepository.save(new Role("ROLE_MANAGER"));

     /*   user = new User("Ivan", "Ivanovic", "ivanivanovic231@hotmail.com",
                "123");*/
        createUserDto = new CreateUserDto(FIRST_NAME, "Ivanovic", EMAIL,
                "123", "ROLE_MANAGER");
        var roleDto = new RoleDto("ROLE_MANAGER"); // koristi se samo u setup metodi, ne treba kao global var
        userDto = new UserDto("Ivan", "Ivanovic", "ivanivanovic22@gmail.com", roleDto);


        // ovo je bilo u testu, da li smo to u brzini zaboravili, ili je imalo neku poentu da ne bude u setupu?
    }

    @Test
    @Order(1)
    @Sql(scripts = {"classpath:data.sql"}, executionPhase = BEFORE_TEST_METHOD)
    void shouldCreateUser() throws Exception {

        var content = objectMapper.writeValueAsString(createUserDto);

        mockMvc.perform(post("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME));

    }
    @Test
    @Order(2)
    @WithUserDetails(EMAIL)
    void shouldGetAllUsers() throws Exception {
        mockMvc.perform(
                        get("/users/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value(FIRST_NAME));
    }

    @Test
    @Order(3)
    @WithUserDetails(EMAIL)
    void shouldGetUserById() throws Exception {
        mockMvc.perform(
                get("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME));
    }
    @Test
    @Order(4)   //ako ide order 5 a delete 4, bacice 404 jer nema nista u in memory h2 bazi!
    @WithUserDetails(EMAIL)
    void shouldUpdateUser() throws Exception {

        var content = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(
                put("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME));
    }

    @Test
    @Order(5)
    @WithUserDetails(EMAIL)
    void shouldDeleteUser() throws Exception {
            mockMvc.perform(
                            delete("/users/{id}", 1)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
            //koji status da vratim za void
            // da sam mu isAccepted kaze da ocekuje isOk, dakle po defaultu ide status 200?
    }
}

//default order of integration tests spring boot
