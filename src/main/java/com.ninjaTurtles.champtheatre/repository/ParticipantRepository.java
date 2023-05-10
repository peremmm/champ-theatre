package com.ninjaTurtles.champtheatre.repository;

import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findByEmployee(Employee employee);
    Optional<Employee> findByFirstName(String firstName);
    Optional<Employee> findByLastName(String lastName);
    Optional<Employee> findByCompany(String company);
}
