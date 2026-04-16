package com.example.crudapp.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.crudapp.dto.LoginRequest;
import com.example.crudapp.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

// ⭐ Load only AuthController
@WebMvcTest(AuthController.class)

// ⭐ Disable security filters
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // ✅ REQUIRED MOCKS
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    // 🔥 IMPORTANT (you missed these)
    @MockBean
    private com.example.crudapp.security.JwtFilter jwtFilter;

    @MockBean
    private com.example.crudapp.security.CustomUserDetailsService userDetailsService;

    private ObjectMapper objectMapper = new ObjectMapper();

    // ✅ SUCCESS CASE
    @Test
    void testLoginSuccess() throws Exception {

        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("password");

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("admin", "password")
        )).thenReturn(null);

        when(jwtUtil.generateToken("admin"))
                .thenReturn("dummy-token");

        mockMvc.perform(post("/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("dummy-token"));
    }

    // ✅ FAILURE CASE
    @Test
    void testLoginFailure() throws Exception {

        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("wrong");

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("admin", "wrong")
        )).thenThrow(new RuntimeException("Bad credentials"));

        mockMvc.perform(post("/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }
}