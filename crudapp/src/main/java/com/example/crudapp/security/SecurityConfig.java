package com.example.crudapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration

public class SecurityConfig {

    private JwtFilter jwtFilter;

    private CustomUserDetailsService userService;

    public SecurityConfig(JwtFilter jwtFilter,
                          CustomUserDetailsService userService){

        this.jwtFilter = jwtFilter;
        this.userService = userService;

    }

    @Bean

    SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception{

        http
        .csrf(csrf -> csrf.disable())

        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/employees/**").authenticated()
                .anyRequest().permitAll()
        )

        .exceptionHandling(ex -> ex
                .authenticationEntryPoint(
                        (request,response,authException) ->
                                response.sendError(401,"Unauthorized")
                )
        )

        .sessionManagement(session ->
                session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                )
        )

        // ⭐ VERY IMPORTANT LINE (YOU MISSED THIS)
        .addFilterBefore(jwtFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

    @Bean

    PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();

    }

    @Bean

    DaoAuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(userService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;

    }

    @Bean

    AuthenticationManager authenticationManager(
            AuthenticationConfiguration config)
            throws Exception{

        return config.getAuthenticationManager();

    }

}