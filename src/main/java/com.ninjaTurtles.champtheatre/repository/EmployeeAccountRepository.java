package com.ninjaTurtles.champtheatre.repository;

import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeAccountRepository extends JpaRepository<EmployeeAccount, Long> {
    Optional<EmployeeAccount> findByUsername(String username);
    Optional<EmployeeAccount> findByStatus(EmployeeAccount.Status status);
    // Add additional methods here, if needed
}
