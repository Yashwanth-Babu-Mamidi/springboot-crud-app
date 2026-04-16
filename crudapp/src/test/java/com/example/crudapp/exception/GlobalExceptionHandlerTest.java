package com.example.crudapp.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.crudapp.controller.EmployeeReadController;
import com.example.crudapp.security.CustomUserDetailsService;
import com.example.crudapp.security.JwtFilter;
import com.example.crudapp.service.EmployeeService;

@WebMvcTest(EmployeeReadController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService service;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @Test
    void testHandleRuntimeException() throws Exception {

        org.mockito.Mockito.when(service.getAll())
                .thenThrow(new RuntimeException("Something went wrong"));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Something went wrong"))
                .andExpect(jsonPath("$.status").value(500));
    }

    @Test
    void testHandleGenericException() throws Exception {

        org.mockito.Mockito.when(service.getAll())
                .thenThrow(new RuntimeException("Generic error"));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Generic error"))
                .andExpect(jsonPath("$.status").value(500));
    }
}