package com.example.crudapp.service;

import com.example.crudapp.entity.Employee;
import com.example.crudapp.repository.EmployeeRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeService service;

    @Test
    void testGetAll() {

        Employee emp = new Employee(1L, "John", "john@mail.com", "IT");

        when(repository.findAll()).thenReturn(List.of(emp));

        List<Employee> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getName());
    }

    @Test
    void testGetById_Success() {

        Employee emp = new Employee(1L, "John", "john@mail.com", "IT");

        when(repository.findById(1L)).thenReturn(Optional.of(emp));

        Employee result = service.getById(1L);

        assertNotNull(result);
        assertEquals("John", result.getName());
    }

    @Test
    void testGetById_NotFound() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.getById(1L);
        });
    }
}