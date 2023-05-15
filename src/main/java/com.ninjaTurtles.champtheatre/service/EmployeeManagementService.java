package com.ninjaTurtles.champtheatre.service;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import com.ninjaTurtles.champtheatre.models.EmployeeRole;

import java.util.List;

public interface EmployeeManagementService {

	List<EmployeeBean> getAllEmployee(String sortField, boolean ascending);

	Employee register(EmployeeBean employeeBean);

	void updateEmployee(EmployeeBean employeeBean);

	EmployeeAccount addEmployeeAccount(EmployeeAccount employeeAccount, EmployeeBean employeeBean);

	EmployeeRole addEmployeeRole(Long roleId, Long employeeId);

	EmployeeBean findEmployeeById(long employeeId);

	EmployeeBean findEmployeeIdByEmail(String employeeEmail);

	void delete(Long employeeId);

	void updateEmployeeAccountStatus(Long employeeId);

	void deleteEmployeeAccount(Long employeeId);
}
