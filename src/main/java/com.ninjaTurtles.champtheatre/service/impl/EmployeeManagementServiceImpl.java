package com.ninjaTurtles.champtheatre.service.impl;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.exception.ServiceException;
import com.ninjaTurtles.champtheatre.models.*;
import com.ninjaTurtles.champtheatre.repository.EmployeeAccountRepository;
import com.ninjaTurtles.champtheatre.repository.EmployeeRepository;
import com.ninjaTurtles.champtheatre.repository.EmployeeRoleRepository;
import com.ninjaTurtles.champtheatre.repository.RoleRepository;
import com.ninjaTurtles.champtheatre.service.EmployeeManagementService;
import com.ninjaTurtles.champtheatre.service.EmployeeRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeManagementServiceImpl implements EmployeeManagementService, EmployeeRoleService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeAccountRepository employeeAccountRepository;
    private final RoleRepository roleRepository;
    private final EmployeeRoleRepository employeeRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeManagementServiceImpl(EmployeeRepository employeeRepository,
                                         EmployeeAccountRepository employeeAccountRepository,
                                         RoleRepository roleRepository, EmployeeRoleRepository employeeRoleRepository,
                                         PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.employeeAccountRepository = employeeAccountRepository;
        this.roleRepository = roleRepository;
        this.employeeRoleRepository = employeeRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<EmployeeBean> getAllEmployee(String sortField, boolean ascending) {
        Sort.Direction direction = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortField);
        List<Employee> employees = employeeRepository.findAll(sort);
        return employees.stream().map(this::mapToEmployeeBean).collect(Collectors.toList());
    }


    private EmployeeBean mapToEmployeeBean(Employee employee) {
        return EmployeeBean.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName().trim())
                .lastName(employee.getLastName().trim())
                .email(employee.getEmail())
                .employeeAccount(employee.getEmployeeAccount())
                .build();
    }

    @Transactional
    @Override
    public EmployeeAccount addEmployeeAccount(EmployeeAccount employeeAccount, EmployeeBean employeeBean) {
        Employee employee = mapToEmployee(employeeBean);
        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new ServiceException("Email already exists.");
        }
        Employee savedEmployee = employeeRepository.save(employee); // persist the employee first

        String firstNamePrefix = savedEmployee.getFirstName().substring(0, Math.min(savedEmployee.getFirstName().length(), 3));
        String lastNamePrefix = savedEmployee.getLastName().substring(0, Math.min(savedEmployee.getLastName().length(), 3));

        String username = (firstNamePrefix + lastNamePrefix).toLowerCase();

        if (employeeAccountRepository.findByUsername(username).isPresent()) {
            int suffix = 1;
            while (employeeAccountRepository.findByUsername(username + suffix).isPresent()) {
                suffix++;
            }
            username += suffix;
        }

        employeeAccount.setUsername(username);
        employeeAccount.setPassword(passwordEncoder.encode(generatePassword()));
        employeeAccount.setEmployee(savedEmployee); // use the saved employee

        employeeAccount.setStatus(EmployeeAccount.Status.INACTIVE);

        //Role Begins
        // Create EmployeeRole and set employeeId and roleId
        EmployeeRole employeeRole = new EmployeeRole();
        EmployeeRoleId employeeRoleId = new EmployeeRoleId(savedEmployee.getId(), null); // roleId will be set later
        employeeRole.setId(employeeRoleId);
        employeeRole.setEmployee(savedEmployee);

        Role role = roleRepository.findByRole("User").orElseThrow(() -> new ServiceException("Role not found.")); // Retrieve the Role with name "User"
        employeeRole.setRole(role); // Set the retrieved Role as the role in EmployeeRole
        employeeRoleRepository.save(employeeRole); // Save the EmployeeRole
        employeeRoleId.setRoleId(role.getId()); // Set the roleId in the EmployeeRoleId
        employeeRoleRepository.save(employeeRole); // Save the modified EmployeeRoleId

        return employeeAccountRepository.save(employeeAccount);
    }
    /**
     *
     * roles (id, role)
     * 101, 'Administrator'
     * 102, 'Reservation Coordinator'
     * 103, 'User'
     */

    public Employee mapToEmployee(EmployeeBean employeeBean) {
        return Employee.builder()
                .id(employeeBean.getId())
                .firstName(employeeBean.getFirstName().trim())
                .lastName(employeeBean.getLastName().trim())
                .email(employeeBean.getEmail())
                .employeeAccount(employeeBean.getEmployeeAccount())
                .build();
    }


    @Override
    public EmployeeBean findEmployeeById(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).get();
        return mapToEmployeeBean(employee);
    }

    @Override
    public EmployeeBean findEmployeeIdByEmail(String employeeEmail) {
        Employee employee = employeeRepository.findByEmail(employeeEmail).get();
        return mapToEmployeeBean(employee);
    }

    @Override
    public void updateEmployee(EmployeeBean employeeBean) {
        Employee employee = mapToEmployee(employeeBean);
        EmployeeAccount employeeAccount = employeeBean.getEmployeeAccount();
        EmployeeRole employeeRole = employeeRoleRepository.findByEmployeeId(employee.getId());
        employeeRoleRepository.save(employeeRole);
        employeeAccountRepository.save(employeeAccount);
        employeeRepository.save(employee);
    }

    @Override
    public void delete(Long employeeId) {
        employeeAccountRepository.delete(employeeAccountRepository.findByEmployeeId(employeeId).get());
        employeeRoleRepository.delete(employeeRoleRepository.findByEmployeeId(employeeId));
        employeeRepository.deleteById(employeeId);
    }


    @Override
    public void deleteEmployeeAccount(Long employeeId) {
        Optional<EmployeeAccount> optionalEmployeeAccount = employeeAccountRepository.findByEmployeeId(employeeId);
        optionalEmployeeAccount.ifPresent(employeeAccount -> {
            employeeAccount.setStatus(EmployeeAccount.Status.TERMINATED);
            employeeAccountRepository.save(employeeAccount);
        });
    }

    @Override
    public Employee findEmployeeByEmail(String email) {
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        // or throw an exception if appropriate
        return employee.orElse(null);
    }

    private String generatePassword() {
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String number = "0123456789";
        StringBuilder result = new StringBuilder();
        Random random = new Random();

        result.append(lower.charAt(random.nextInt(lower.length())));
        result.append(upper.charAt(random.nextInt(upper.length())));
        result.append(number.charAt(random.nextInt(number.length())));

        for (int i = 3; i < 10; i++) {
            String characters = lower + upper + number;
            result.append(characters.charAt(random.nextInt(characters.length())));
        }

        return result.toString();
    }

    @Override
    public String getEmployeeEmailById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with ID: " + employeeId));

        return employee.getEmail();
    }

    @Override
    public EmployeeRole findRoleByEmployeeId(Long employeeId) {
        return employeeRoleRepository.findByEmployeeId(employeeId);
    }
}
