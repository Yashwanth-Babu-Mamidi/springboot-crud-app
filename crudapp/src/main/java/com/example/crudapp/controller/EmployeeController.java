package com.example.crudapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.crudapp.entity.Employee;
import com.example.crudapp.service.EmployeeService;

@RestController
@RequestMapping("/employees")

public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service){
        this.service = service;
    }

    // Create
    @PostMapping
    public Employee create(@RequestBody Employee emp){
        return service.save(emp);
    }

    // Get all
    @GetMapping
    public List<Employee> getAll(){
        return service.getAll();
    }

    // Get by ID  ✅ FIXED
    @GetMapping("/{id}")
    public Employee getById(@PathVariable("id") Long id){
        return service.getById(id);
    }

    // Update ✅ FIXED
    @PutMapping("/{id}")
    public Employee update(@PathVariable("id") Long id,
                           @RequestBody Employee emp){

        emp.setId(id);

        return service.update(emp);
    }

    // Delete ✅ FIXED
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){

        service.delete(id);

        return "Employee deleted successfully";
    }

}