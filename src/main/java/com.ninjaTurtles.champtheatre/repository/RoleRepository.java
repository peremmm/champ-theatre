//package com.ninjaTurtles.champtheatre.repository;
//
//import com.ninjaTurtles.champtheatre.models.EmployeeRole;
//import com.ninjaTurtles.champtheatre.models.Role;
//import com.ninjaTurtles.champtheatre.models.RoleModule;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//import java.util.Set;
//
//public interface RoleRepository extends JpaRepository<Role, Long> {
//    Optional<Role> findByRole(String role);
//    Optional<Role> findByModule(Set<RoleModule> module);
//    Optional<Role> findByEmployee(Set<EmployeeRole> employee);
//}
