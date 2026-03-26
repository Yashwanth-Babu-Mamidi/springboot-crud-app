package com.example.crudapp.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.crudapp.entity.Employee;
import com.example.crudapp.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository){
        this.repository = repository;
    }

    public Employee save(Employee emp){
        return repository.save(emp);
    }

    public List<Employee> getAll(){
        return repository.findAll();
    }

    public Employee getById(Long id){

        return repository.findById(id)
                .orElseThrow(() -> 
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Employee not found with id : " + id
                    ));
    }

    public Employee update(Employee emp){

        Employee existingEmployee = repository.findById(emp.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Employee not found with id : " + emp.getId()
                ));

        existingEmployee.setName(emp.getName());
        existingEmployee.setEmail(emp.getEmail());
        existingEmployee.setDepartment(emp.getDepartment());

        return repository.save(existingEmployee);
    }
    public void delete(Long id){

        Employee emp = repository.findById(id)
                .orElseThrow(() -> 
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Employee not found with id : " + id
                    ));

        repository.delete(emp);
    
    }

}