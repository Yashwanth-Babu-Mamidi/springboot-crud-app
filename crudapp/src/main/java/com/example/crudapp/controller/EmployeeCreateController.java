package com.example.crudapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crudapp.entity.Employee;
import com.example.crudapp.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/employees")

public class EmployeeCreateController {

    private EmployeeService service;

    public EmployeeCreateController(EmployeeService service){
        this.service = service;
    }

    @PostMapping

    @PreAuthorize("hasRole('ADMIN')")

    @Operation(summary="Create employee",
    description="Creates a new employee record")

    @ApiResponse(responseCode="200",
    description="Employee created successfully")

    public Employee create(@RequestBody Employee emp){

        return service.save(emp);

    }

}