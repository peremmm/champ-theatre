package com.ninjaTurtles.champtheatre.service;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.EmployeeAccount;

import java.util.List;

public interface EmployeeManagementService {

	List<EmployeeBean> getAllEmployee();

	Employee register(Employee employee);

	void updateEmployeeDetails(Employee employee);

	void updateEmployeeAccountStatus(EmployeeAccount employeeAccount);

	void deleteEmployeeAccount(EmployeeAccount employeeAccount);

	EmployeeAccount addEmployeeAccount(EmployeeAccount employeeAccount, Employee employee);

	EmployeeBean findEmployeeById(long employeeId);

	void updateEmployeeId(EmployeeBean employeeBean);
}
