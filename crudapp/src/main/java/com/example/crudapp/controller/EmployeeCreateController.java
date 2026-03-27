package com.example.crudapp.controller;

import org.springframework.web.bind.annotation.*;

import com.example.crudapp.entity.Employee;
import com.example.crudapp.service.EmployeeService;

@RestController
@RequestMapping("/employees")

public class EmployeeCreateController {

    private EmployeeService service;

    public EmployeeCreateController(EmployeeService service){
        this.service = service;
    }

    @PostMapping

    public Employee create(@RequestBody Employee emp){

        return service.save(emp);

    }

}