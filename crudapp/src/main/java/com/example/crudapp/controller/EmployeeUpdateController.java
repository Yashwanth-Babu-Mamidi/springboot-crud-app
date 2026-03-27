package com.example.crudapp.controller;

import org.springframework.web.bind.annotation.*;

import com.example.crudapp.entity.Employee;
import com.example.crudapp.service.EmployeeService;

@RestController
@RequestMapping("/employees")

public class EmployeeUpdateController {

    private EmployeeService service;

    public EmployeeUpdateController(EmployeeService service){
        this.service = service;
    }

    @PutMapping("/{id}")

    public Employee update(@PathVariable Long id,
                           @RequestBody Employee emp){

        emp.setId(id);

        return service.update(emp);

    }

}