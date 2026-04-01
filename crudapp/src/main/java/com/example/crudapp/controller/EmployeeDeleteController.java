package com.example.crudapp.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crudapp.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/employees")

public class EmployeeDeleteController {

    private EmployeeService service;

    public EmployeeDeleteController(EmployeeService service){
        this.service = service;
    }

    @DeleteMapping("/{id}")
    
    @Operation(summary="Delete employee",
    description="Deletes employee by ID")

    @ApiResponse(responseCode="200",
    description="Employee deleted")

    public String delete(@PathVariable Long id){

        service.delete(id);

        return "Employee deleted successfully";

    }

}