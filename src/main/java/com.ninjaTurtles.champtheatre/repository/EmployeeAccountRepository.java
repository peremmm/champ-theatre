package com.ninjaTurtles.champtheatre.repository;

import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeAccountRepository extends JpaRepository<EmployeeAccount, Long> {
    // Add additional methods here, if needed
}
