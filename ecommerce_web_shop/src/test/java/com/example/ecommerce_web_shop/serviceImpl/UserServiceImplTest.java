package com.example.ecommerce_web_shop.serviceImpl;

import com.example.ecommerce_web_shop.dto.CreateUserDto;
import com.example.ecommerce_web_shop.dto.RoleDto;
import com.example.ecommerce_web_shop.dto.UserDto;
import com.example.ecommerce_web_shop.exception.NotFoundException;
import com.example.ecommerce_web_shop.mapper.UserMapper;
import com.example.ecommerce_web_shop.model.Role;
import com.example.ecommerce_web_shop.model.User;
import com.example.ecommerce_web_shop.repositories.RoleRepository;
import com.example.ecommerce_web_shop.repositories.UserRepository;
import com.example.ecommerce_web_shop.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private Role role;

    private User user;
    private UserDto userDto;
    private CreateUserDto createUserDto;

    @BeforeEach
    void setUp() {
        role = new Role("ROLE_MANAGER");
        var roleDto = new RoleDto("ROLE_MANAGER"); // koristi se samo u setup metodi, ne treba kao global var
        user = new User("Ivan", "Ivanovic", "ivanivanovic22@gmail.com");
        userDto = new UserDto("Ivan", "Ivanovic", "ivanivanovic22@gmail.com", roleDto);
        createUserDto = new CreateUserDto("Ivan", "Ivanovic", "ivanivanovic22@gmail.com",
                "123", "ROLE_MANAGER");
    }

    @Test
    void shouldReturnAllUsers(){
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(userMapper.map(user)).thenReturn(userDto); // ovo mockujem
        var result = userService.getUsers();
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).map(user); // dam mu verify da je proslo tacno toliko puta
        assertEquals(1, result.size());
        assertEquals("Ivan", result.get(0).getFirstName()); // assertujem da je to mockovano zapravo to sto treba da bude
    }

    @Test
    void shouldReturnEmptyListWhenNoUsers(){
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        var result = userService.getUsers();
        verify(userRepository, times(1)).findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldCreateUser(){
        when(userMapper.map(createUserDto)).thenReturn(user);
        when(roleRepository.findByName(createUserDto.roleName())).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(any())).thenReturn("");
        when(userRepository.save(user)).thenReturn(user);  // ovo je savedUser u servisu
        when(userMapper.map(user)).thenReturn(userDto);
        var result = userService.createUser(createUserDto);
        verify(userMapper, times(1)).map(createUserDto);
        verify(roleRepository, times(1)).findByName(createUserDto.roleName());
        verify(passwordEncoder, times(1)).encode(any());
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).map(user);
        assertEquals("ivanivanovic22@gmail.com", result.getEmail());
        assertEquals("ROLE_MANAGER", result.getRole().name());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenNoRoleFound(){
        when(roleRepository.findByName(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.createUser(createUserDto));
        verify(roleRepository, times(1)).findByName(any());
    }

    @Test
    void shouldGetUserById(){
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(userMapper.map(user)).thenReturn(userDto);
        var result = userService.getUserById(anyInt());
        verify(userRepository, times(1)).findById(anyInt());
        verify(userMapper, times(1)).map(user);
        assertEquals("Ivan", result.getFirstName());
        assertEquals("ROLE_MANAGER", result.getRole().name());
    }

    @Test
    void shouldThrowNoUserFoundWhenFindUserById(){
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.getUserById(anyInt()));
        verify(userRepository, times(1)).findById(anyInt());
    }

    @Test
    void shouldUpdateUser(){
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(userMapper.map(any(User.class))).thenReturn(userDto);
        when(userRepository.save(any())).thenReturn(user);
        var result = userService.updateUser(1, userDto);
        verify(userRepository, times(1)).findById(anyInt());
        verify(userMapper, times(1)).map(any(User.class));
        assertEquals(user.getFirstName(), result.getFirstName());
    }   //ovde ne stavljam zeljeni string nego mi je zeljeno zapravo to sto je u useru
}
