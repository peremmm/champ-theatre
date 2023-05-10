package com.ninjaTurtles.champtheatre.service.impl;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import com.ninjaTurtles.champtheatre.repository.EmployeeRepository;
import com.ninjaTurtles.champtheatre.service.EmployeeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeManagementServiceImpl implements EmployeeManagementService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeManagementServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void register(Employee employee) {

    }

    @Override
    public List<EmployeeBean> getAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map((employee) -> mapToEmployeeBean(employee)).collect(Collectors.toList());
    }

    private EmployeeBean mapToEmployeeBean(Employee employee) {
        EmployeeBean employeeBean = EmployeeBean.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .build();
        return employeeBean;
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

    @Override
    public void addEmployeeAccount(Employee employeeId, EmployeeAccount employeeAccount) {

    }
}
