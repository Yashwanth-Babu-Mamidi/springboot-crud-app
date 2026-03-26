
package com.example.crudapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.crudapp.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long>{

}
