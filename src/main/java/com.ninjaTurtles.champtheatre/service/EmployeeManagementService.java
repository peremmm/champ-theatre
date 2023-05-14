package com.ninjaTurtles.champtheatre.service;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import com.ninjaTurtles.champtheatre.models.EmployeeRole;

import java.util.List;

public interface EmployeeManagementService {

	List<EmployeeBean> getAllEmployee();

	Employee register(Employee employee);

	void updateEmployee(EmployeeBean employeeBean);

	void updateEmployeeAccountStatus(EmployeeAccount employeeAccount);

	void deleteEmployeeAccount(EmployeeAccount employeeAccount);

	EmployeeAccount addEmployeeAccount(EmployeeAccount employeeAccount, Employee employee);

	EmployeeRole addEmployeeRole(Long roleId, Long employeeId);

	EmployeeBean findEmployeeById(long employeeId);

	EmployeeBean findEmployeeIdByEmail(String employeeEmail);


}
