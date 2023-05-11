package com.ninjaTurtles.champtheatre.service.impl;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import com.ninjaTurtles.champtheatre.repository.EmployeeAccountRepository;
import com.ninjaTurtles.champtheatre.repository.EmployeeRepository;
import com.ninjaTurtles.champtheatre.service.EmployeeManagementService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Employee register(Employee employee) {
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
        String password = RandomStringUtils.random(10, true, true);
        employeeAccount.setUsername(employee.getFirstName() + employee.getLastName());
        employeeAccount.setPassword(password);
        employeeAccount.setEmployee(employee);
        employeeAccount.setStatus(EmployeeAccount.Status.INACTIVE);

        return employeeAccountRepository.save(employeeAccount);
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


}
