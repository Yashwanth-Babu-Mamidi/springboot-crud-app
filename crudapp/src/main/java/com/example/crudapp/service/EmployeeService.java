package com.example.crudapp.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.crudapp.entity.Employee;
import com.example.crudapp.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeService.class);

    private EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository){
        this.repository = repository;
    }

    public Employee save(Employee emp){

        logger.info("Saving employee: {}", emp.getName());
        logger.debug("Employee details: {}", emp);

        Employee saved = repository.save(emp);

        logger.info("Employee saved successfully with id: {}", saved.getId());

        return saved;
    }

    public List<Employee> getAll(){

        logger.info("Fetching all employees");

        List<Employee> list = repository.findAll();

        logger.debug("Total employees found: {}", list.size());

        return list;
    }

    public Employee getById(Long id){

        logger.info("Fetching employee with id: {}", id);

        return repository.findById(id)
                .orElseThrow(() -> {

                    logger.error("Employee not found with id: {}", id);

                    return new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Employee not found with id : " + id
                    );
                });
    }

    public Employee update(Employee emp){

        logger.info("Updating employee with id: {}", emp.getId());

        Employee existingEmployee = repository.findById(emp.getId())
                .orElseThrow(() -> {

                    logger.error("Employee not found for update: {}", emp.getId());

                    return new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Employee not found with id : " + emp.getId()
                    );
                });

        logger.debug("Old Employee Data: {}", existingEmployee);

        existingEmployee.setName(emp.getName());
        existingEmployee.setEmail(emp.getEmail());
        existingEmployee.setDepartment(emp.getDepartment());

        logger.debug("Updated Employee Data: {}", existingEmployee);

        Employee updated = repository.save(existingEmployee);

        logger.info("Employee updated successfully with id: {}", updated.getId());

        return updated;
    }

    public void delete(Long id){

        logger.info("Deleting employee with id: {}", id);

        Employee emp = repository.findById(id)
                .orElseThrow(() -> {

                    logger.error("Employee not found for delete: {}", id);

                    return new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Employee not found with id : " + id
                    );
                });

        repository.delete(emp);

        logger.info("Employee deleted successfully with id: {}", id);
    }

}