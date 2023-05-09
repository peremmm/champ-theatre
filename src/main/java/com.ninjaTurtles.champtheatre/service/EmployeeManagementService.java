package com.ninjaTurtles.champtheatre.service;

import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.EmployeeAccount;

import java.util.List;


public interface EmployeeManagementService {
	
	void register(Employee employee);
	
	List<Employee> getAllEmployee();
	
	void updateEmployeeDetails(Employee employee);
	
	void updateEmployeeAccountStatus(EmployeeAccount employeeAccount);
	
	void deleteEmployeeAccount(EmployeeAccount employeeAccount);
	
	void addEmployeeAccount(Employee employeeId, EmployeeAccount employeeAccount);

}
