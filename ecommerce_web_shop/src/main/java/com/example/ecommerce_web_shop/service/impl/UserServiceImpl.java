package com.example.ecommerce_web_shop.service.impl;


import com.example.ecommerce_web_shop.dto.CreateUserDto;
import com.example.ecommerce_web_shop.dto.UserDto;
import com.example.ecommerce_web_shop.exception.NotFoundException;
import com.example.ecommerce_web_shop.mapper.UserMapper;
import com.example.ecommerce_web_shop.model.User;
import com.example.ecommerce_web_shop.repositories.RoleRepository;
import com.example.ecommerce_web_shop.repositories.UserRepository;
import com.example.ecommerce_web_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getUsers() {
       return userRepository.findAll().stream().map(userMapper::map).collect(Collectors.toList());
    }

    @Override
    public UserDto findUser(int id) {       // GET jednog usera
        var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("user does not exist"));
        return userMapper.map(user);
    }

    @Override
    public UserDto createUser(CreateUserDto createUserDto) {

        var user = userMapper.map(createUserDto);
        var role = roleRepository.findByName(createUserDto.roleName())
                .orElseThrow(() -> new NotFoundException("not found")); // mora optional u Role repo !!!

        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // trebalo bi da radi?
        userRepository.save(user);

        return userMapper.map(user);
    }

    @Override
    public UserDto updateUser(int id, UserDto userDto) {
        User updatedUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("user does not exist!"));

        updatedUser.setFirstName(userDto.getFirstName());
        updatedUser.setLastName(userDto.getLastName());
        updatedUser.setEmail(userDto.getEmail());  // da li moze da kreira/updatuje novi basket kako hoce? Ne.
        var savedEntity = userRepository.save(updatedUser);
        return userMapper.map(savedEntity);
    }

    @Override
    public void deleteUser(int id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public void addRoleToUser(int userId, int roleId) {     // redundant al neka ga

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("stagod"));

        var role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("stagod"));

        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { // nemam username pa cu dati da bude email

        var user = userRepository.findByEmail(email);

        if (user == null){
            throw new UsernameNotFoundException("User not found in the database");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    /*public void refreshToken(HttpServletRequest request, HttpServletResponse response, User user, int id){
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 5000*//*60 * 60 * 1000*//*))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .sign(algorithm);

                String refresh_token = JWT.create()     // ne trebaju role
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 4320 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .sign(algorithm);

       *//* response.setHeader("access_token", access_token);
        response.setHeader("refresh_token", refresh_token);*//*

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }*/
}

/* @Override
    public UserDto createUser(UserDto userDto) {
        var savedEntity = userRepository.save(userMapper.map(userDto));
        return userMapper.map(savedEntity);

        STARI CREATE PRE SECURITY
    }*/
