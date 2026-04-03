package com.example.crudapp.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crudapp.entity.Employee;
import com.example.crudapp.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="Employee APIs",
description="CRUD operations for Employee")
@RestController
@RequestMapping("/employees")

public class EmployeeReadController {

    private EmployeeService service;

    public EmployeeReadController(EmployeeService service){
        this.service = service;
    }

    @GetMapping
    
    @PreAuthorize("hasAnyRole('ADMIN','USER')")

    @Operation(summary="Get all employees",
    description="Returns list of all employees")

    @ApiResponses(value = {

    @ApiResponse(responseCode="200",
    description="Employees fetched successfully"),

    @ApiResponse(responseCode="401",
    description="Unauthorized access")
    
    })
    
    public List<Employee> getAll(){

        return service.getAll();

    }

    @GetMapping("/{id}")
    
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    
    @Operation(summary="Get employee by ID",
    description="Fetch employee using employee ID")

    @ApiResponses(value={

    @ApiResponse(responseCode="200",
    description="Employee found"),

    @ApiResponse(responseCode="404",
    description="Employee not found")

    })

    public Employee getById(@PathVariable("id") Long id){

        return service.getById(id);

    }

}