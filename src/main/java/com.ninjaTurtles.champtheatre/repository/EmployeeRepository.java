//package com.ninjaTurtles.champtheatre.repository;
//
//import com.ninjaTurtles.champtheatre.models.Employee;
//import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.Optional;
//
//public interface EmployeeRepository extends JpaRepository<Employee, Long> {
////    @Query("SELECT employee FROM Employee e where e.id = :id")
////    Employee findById(@Param("id") int id);
//
//    Optional<Employee> findByFirstName(String firstName);
//
//    Optional<Employee> findByLastName(String lastName);
//
//    Optional<Employee> findByEmail(String email);
//
//    Optional<Employee> findByEmployeeAccount(EmployeeAccount employeeAccount);
//}
//
