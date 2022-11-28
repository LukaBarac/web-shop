package com.example.ecommerce_web_shop.controller;

import com.example.ecommerce_web_shop.dto.CreateUserDto;
import com.example.ecommerce_web_shop.dto.RoleDto;
import com.example.ecommerce_web_shop.dto.UserDto;
import com.example.ecommerce_web_shop.exception.NotFoundException;
import com.example.ecommerce_web_shop.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private CreateUserDto createUserDto;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        var roleDto = new RoleDto("ROLE_MANAGER");
        createUserDto = new CreateUserDto("Ivan", "Ivanovic", "ivanivanovic22@gmail.com",
                "123", "ROLE_MANAGER");
        userDto = new UserDto("Ivan", "Ivanovic", "ivanivanovic22@gmail.com", roleDto);
    }

    @Test
    void shouldCreateUser() throws Exception {
        when(userService.createUser(any(CreateUserDto.class))).thenReturn(userDto);
        var content = objectMapper.writeValueAsString(createUserDto);
        mockMvc.perform(
                post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        when(userService.getUsers()).thenReturn(Collections.singletonList(userDto));

        var content = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(
                get("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(jsonPath("$[0].firstName").value("Ivan"));
    }

    @Test
    void shouldGetUserById() throws Exception {
        when(userService.findUser(1)).thenReturn(userDto);
        var content = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(
                        get("/users/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isOk());
                       /* get("/users/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(jsonPath("$[0].firstName").value("Ivan"));*/
    }

    @Test
    void shouldThrowNotFoundExceptionWhenGetUserById() throws Exception {
        when(userService.findUser(anyInt())).thenThrow(NotFoundException.class);
        var content = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(
                get("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldUpdateUser() throws Exception {
        when(userService.updateUser(1, userDto)).thenReturn(userDto);
        var content = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(
                put("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteUser() throws Exception{
        doNothing().when(userService).deleteUser(1); // sta je fora
        //odradi userService.deleteUser , sve u zagradi kao u ostalim metodama, cudno se ponasa autofill kada je void endpoint
        mockMvc.perform(
                delete("/users/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddRoleToUser() throws Exception {
        doNothing().when(userService).addRoleToUser(1, 1);
        mockMvc.perform(
                patch("/users/{id}/{id}", 1, 1)) // da li je putanja ok?
                .andExpect(status().isOk());
    }
}

