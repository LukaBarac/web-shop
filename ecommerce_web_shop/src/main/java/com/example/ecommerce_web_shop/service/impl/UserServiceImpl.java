package com.example.ecommerce_web_shop.service.impl;


import com.example.ecommerce_web_shop.dto.CreateUserDto;
import com.example.ecommerce_web_shop.dto.UserDto;
import com.example.ecommerce_web_shop.exception.NotFoundException;
import com.example.ecommerce_web_shop.mapper.UserMapper;
import com.example.ecommerce_web_shop.repositories.RoleRepository;
import com.example.ecommerce_web_shop.repositories.UserRepository;
import com.example.ecommerce_web_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

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
    public UserDto getUserById(int id) {
        var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("user does not exist"));
        return userMapper.map(user);
    }

    @Override
    public UserDto createUser(CreateUserDto createUserDto) {
        var user = userMapper.map(createUserDto);
        var role = roleRepository.findByName(createUserDto.roleName())
                .orElseThrow(() -> new NotFoundException("Could not find user with that role name"));
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userMapper.map(user);
    }

    @Override
    public UserDto updateUser(int id, UserDto userDto) {
        var updatedUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("user does not exist!"));
        updatedUser = userMapper.mapUpdatedUser(userDto, updatedUser); // da li da stoji void mapper ili User, tj da li da ostavim promenljivu ovde ili ne
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
}
