package com.ninjaTurtles.champtheatre.repository;

import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.Module;
import com.ninjaTurtles.champtheatre.models.Role;
import com.ninjaTurtles.champtheatre.models.RoleModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(String role);
    Set<Role> findAllByModule(Module module);
    Set<Role> findAllByEmployee(Employee employee);

    // Add the following method to retrieve a role by ID
    Optional<Role> findById(Long id);
}

