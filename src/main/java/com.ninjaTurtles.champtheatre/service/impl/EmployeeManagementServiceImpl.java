package com.ninjaTurtles.champtheatre.service.impl;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.exception.ServiceException;
import com.ninjaTurtles.champtheatre.models.*;
import com.ninjaTurtles.champtheatre.repository.EmployeeAccountRepository;
import com.ninjaTurtles.champtheatre.repository.EmployeeRepository;
import com.ninjaTurtles.champtheatre.repository.RoleRepository;
import com.ninjaTurtles.champtheatre.service.EmployeeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeManagementServiceImpl implements EmployeeManagementService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeAccountRepository employeeAccountRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public EmployeeManagementServiceImpl(EmployeeRepository employeeRepository, EmployeeAccountRepository employeeAccountRepository, RoleRepository roleRepository){
        this.employeeRepository = employeeRepository;
        this.employeeAccountRepository = employeeAccountRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<EmployeeBean> getAllEmployee(String sortField, boolean ascending) {
        Sort.Direction direction = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortField);
        List<Employee> employees = employeeRepository.findAll(sort);
        return employees.stream().map(this::mapToEmployeeBean).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Employee register(EmployeeBean employeebean) throws ServiceException {
        Employee employee = mapToEmployee(employeebean);
        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new ServiceException("Email already exists.");
        }
        return employeeRepository.save(employee);
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
        employeeAccount.setPassword(generatePassword());
        employeeAccount.setEmployee(savedEmployee); // use the saved employee

        employeeAccount.setStatus(EmployeeAccount.Status.INACTIVE);

        return employeeAccountRepository.save(employeeAccount);
    }

    private Employee mapToEmployee(EmployeeBean employeeBean) {
        return Employee.builder()
                .id(employeeBean.getId())
                .firstName(employeeBean.getFirstName().trim())
                .lastName(employeeBean.getLastName().trim())
                .email(employeeBean.getEmail())
                .employeeAccount(employeeBean.getEmployeeAccount())
                .build();
    }

    /**
     * TO DO
     * roles (id, role) Initial
     * 101, 'Administrator'
     * 102, 'Reservation Coordinator'
     * 103, 'User'
     */
    @Transactional
    @Override
    public EmployeeRole addEmployeeRole(Long roleId, Long employeeId) {
        EmployeeRole employeeRole = new EmployeeRole();
        EmployeeRoleId id = new EmployeeRoleId();
        id.setRoleId(roleId);
        id.setEmployeeId(employeeId);
        employeeRole.setId(id);
        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        if (employee != null) {
            employee.setRoles((Set<EmployeeRole>) employeeRole);
        }
        System.out.println("EmployeeID: " + employeeId + "\nRoleId: " + roleId);
        return null; //employeeRoleRepository.save(employeeRole);
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
        employeeAccountRepository.save(employeeAccount);
        employeeRepository.save(employee);
    }

    @Override
    public void delete(Long employeeId) {
        employeeAccountRepository.delete(employeeAccountRepository.findByEmployeeId(employeeId).get());
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




}
