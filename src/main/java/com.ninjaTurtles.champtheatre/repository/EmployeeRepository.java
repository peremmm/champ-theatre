package com.ninjaTurtles.champtheatre.repository;

import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByFirstName(String firstName);

    Optional<Employee> findByLastName(String lastName);

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByEmployeeAccount(EmployeeAccount employeeAccount);
}

