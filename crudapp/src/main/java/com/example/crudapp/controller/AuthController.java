package com.example.crudapp.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crudapp.dto.AuthResponse;
import com.example.crudapp.dto.LoginRequest;
import com.example.crudapp.security.JwtUtil;

@RestController
@RequestMapping("/auth")

public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil){

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")

    public AuthResponse login(@RequestBody LoginRequest request){

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token =
                jwtUtil.generateToken(request.getUsername());

        return new AuthResponse(token);

    }

}