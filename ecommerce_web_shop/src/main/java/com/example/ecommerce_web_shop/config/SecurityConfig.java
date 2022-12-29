package com.example.ecommerce_web_shop.config;

import com.example.ecommerce_web_shop.filter.CustomAuthenticationFilter;
import com.example.ecommerce_web_shop.filter.CustomAuthorizationFilter;
import com.example.ecommerce_web_shop.service.UserService;
import com.example.ecommerce_web_shop.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers(POST, "/role/").permitAll();
        http.authorizeRequests().antMatchers(POST, "/users/").permitAll();  // mora zbog create drop
        http.authorizeRequests().antMatchers(GET, "/products/").permitAll();
        http.authorizeRequests().antMatchers(GET, "/products/**").permitAll(); // samo za probu retrofita!!!!!
        http.authorizeRequests().antMatchers(GET, "/users/**").hasAnyAuthority("ROLE_MANAGER");
        http.authorizeRequests().antMatchers(PUT, "/users/**").hasAnyAuthority("ROLE_MANAGER");
        http.authorizeRequests().antMatchers(DELETE, "/users/**").hasAnyAuthority("ROLE_MANAGER");
//        http.authorizeRequests().antMatchers(POST, "/products/**").hasAnyAuthority("ROLE_MANAGER");
        http.authorizeRequests().antMatchers(POST, "/products/**").permitAll(); //SAMO ZA RETROFIT PROBU
        http.authorizeRequests().antMatchers(DELETE, "/products/**").hasAnyAuthority("ROLE_MANAGER");
        http.authorizeRequests().antMatchers(GET, "/basket/").hasAnyAuthority("ROLE_MANAGER");
        http.authorizeRequests().antMatchers(GET, "/basket/ceobasket/{basketId}").hasAnyAuthority("ROLE_MANAGER", "ROLE_CUSTOMER");
        http.authorizeRequests().antMatchers(PUT, "/basket/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_CUSTOMER");
        http.authorizeRequests().antMatchers(POST, "/order/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_CUSTOMER");
        /*http.authorizeRequests().antMatchers(POST, "/order/**").permitAll();*/
        http.authorizeRequests().antMatchers(GET, "/order/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_CUSTOMER");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
