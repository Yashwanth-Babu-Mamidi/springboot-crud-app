package com.example.crudapp.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crudapp.entity.Employee;
import com.example.crudapp.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/employees")

public class EmployeeUpdateController {

    private EmployeeService service;

    public EmployeeUpdateController(EmployeeService service){
        this.service = service;
    }

    @PutMapping("/{id}")
    
    @Operation(summary="Update employee",
    description="Updates employee details")

    @ApiResponse(responseCode="200",
    description="Employee updated")

    public Employee update(@PathVariable Long id,
                           @RequestBody Employee emp){

        emp.setId(id);

        return service.update(emp);

    }

}