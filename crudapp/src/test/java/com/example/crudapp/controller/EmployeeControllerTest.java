package com.example.crudapp.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.crudapp.entity.Employee;
import com.example.crudapp.service.EmployeeService;
import com.example.crudapp.security.JwtFilter;
import com.example.crudapp.security.CustomUserDetailsService;

@WebMvcTest(EmployeeReadController.class)   // ✅ ONLY ONCE
@AutoConfigureMockMvc(addFilters = false)   // ✅ ONLY ONCE
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService service;

    // ✅ Mock security dependencies
    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @Test
    void testGetAllEmployees() throws Exception {

        Employee emp1 = new Employee(1L, "John", "john@mail.com", "IT");
        Employee emp2 = new Employee(2L, "Jane", "jane@mail.com", "HR");

        when(service.getAll()).thenReturn(List.of(emp1, emp2));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].department").value("HR"));
    }
}