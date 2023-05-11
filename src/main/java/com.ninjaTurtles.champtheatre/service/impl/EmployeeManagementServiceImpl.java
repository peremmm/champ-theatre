package com.ninjaTurtles.champtheatre.service.impl;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.exception.ServiceException;
import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import com.ninjaTurtles.champtheatre.repository.EmployeeAccountRepository;
import com.ninjaTurtles.champtheatre.repository.EmployeeRepository;
import com.ninjaTurtles.champtheatre.service.EmployeeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class EmployeeManagementServiceImpl implements EmployeeManagementService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeAccountRepository employeeAccountRepository;

    @Autowired
    public EmployeeManagementServiceImpl(EmployeeRepository employeeRepository, EmployeeAccountRepository employeeAccountRepository){
        this.employeeRepository = employeeRepository;
        this.employeeAccountRepository = employeeAccountRepository;
    }

    @Override
    public Employee register(Employee employee) throws ServiceException {
        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new ServiceException("Email already exists.");
        }
        return employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeBean> getAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(this::mapToEmployeeBean).collect(Collectors.toList());
    }

    private EmployeeBean mapToEmployeeBean(Employee employee) {
        return EmployeeBean.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .build();
    }

    @Override
    public EmployeeAccount addEmployeeAccount(EmployeeAccount employeeAccount, Employee employee) {

        /**
         * Get the first three characters of the first name and last name
         * If either is less than 3 characters, pass all
         *
         * (e.g.) Lastname = "Yu"
         */
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


    @Override
    public EmployeeBean findEmployeeById(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).get();
        return mapToEmployeeBean(employee);
    }

    @Override
    public void updateEmployeeId(EmployeeBean employeeBean) {
//        Employee employee = mapToEmployeeBean()
    }

    @Override
    public void updateEmployeeDetails(Employee employee) {

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
