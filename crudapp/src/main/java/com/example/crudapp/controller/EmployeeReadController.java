package com.example.crudapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.crudapp.entity.Employee;
import com.example.crudapp.service.EmployeeService;

@RestController
@RequestMapping("/employees")

public class EmployeeReadController {

    private EmployeeService service;

    public EmployeeReadController(EmployeeService service){
        this.service = service;
    }

    @GetMapping

    public List<Employee> getAll(){

        return service.getAll();

    }

    @GetMapping("/{id}")

    public Employee getById(@PathVariable("id") Long id){

        return service.getById(id);

    }

}