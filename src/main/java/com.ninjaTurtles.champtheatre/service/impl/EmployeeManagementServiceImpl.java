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

    @Transactional
    @Override
    public Employee register(EmployeeBean employeebean) throws ServiceException {
        Employee employee = mapToEmployee(employeebean);
        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new ServiceException("Email already exists.");
        }
        return employeeRepository.save(employee);
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
                .build();
    }

    @Transactional
    @Override
    public EmployeeAccount addEmployeeAccount(EmployeeAccount employeeAccount, EmployeeBean employeeBean) {

        /**
         * Get the first three characters of the first name and last name
         * If either is less than 3 characters, pass all
         *
         * (e.g.) Lastname = "Yu"
         */
        Employee employee = mapToEmployee(employeeBean);
        String firstNamePrefix = employee.getFirstName().substring(0, Math.min(employee.getFirstName().length(), 3));
        String lastNamePrefix = employee.getLastName().substring(0, Math.min(employee.getLastName().length(), 3));

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
        employeeAccount.setEmployee(employee);
        employeeAccount.setStatus(EmployeeAccount.Status.INACTIVE);

        return employeeAccountRepository.save(employeeAccount);
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
        employeeRepository.save(employee);
    }

    private Employee mapToEmployee(EmployeeBean employeeBean) {
        return Employee.builder()
                .id(employeeBean.getId())
                .firstName(employeeBean.getFirstName().trim())
                .lastName(employeeBean.getLastName().trim())
                .email(employeeBean.getEmail())
                .build();
    }

    @Override
    public void updateEmployeeAccountStatus(EmployeeAccount employeeAccount) {

    }

    @Override
    public void deleteEmployeeAccount(EmployeeAccount employeeAccount) {

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
