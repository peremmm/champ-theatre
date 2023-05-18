package com.ninjaTurtles.champtheatre.repository;

import com.ninjaTurtles.champtheatre.models.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Long> {

    EmployeeRole findByEmployeeId(Long employeeId);
}
