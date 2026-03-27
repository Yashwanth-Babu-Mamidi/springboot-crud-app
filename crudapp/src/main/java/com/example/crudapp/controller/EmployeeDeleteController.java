package com.example.crudapp.controller;

import org.springframework.web.bind.annotation.*;

import com.example.crudapp.service.EmployeeService;

@RestController
@RequestMapping("/employees")

public class EmployeeDeleteController {

    private EmployeeService service;

    public EmployeeDeleteController(EmployeeService service){
        this.service = service;
    }

    @DeleteMapping("/{id}")

    public String delete(@PathVariable Long id){

        service.delete(id);

        return "Employee deleted successfully";

    }

}