package com.ninjaTurtles.champtheatre.service;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.EmployeeAccount;

import java.util.List;
import java.util.Optional;

public interface EmployeeManagementService {

	List<EmployeeBean> getAllEmployee(String sortField, boolean ascending);

	void updateEmployee(EmployeeBean employeeBean);

	EmployeeAccount addEmployeeAccount(EmployeeAccount employeeAccount, EmployeeBean employeeBean);

	EmployeeBean findEmployeeById(long employeeId);

	EmployeeBean findEmployeeIdByEmail(String employeeEmail);

	void delete(Long employeeId);

	void deleteEmployeeAccount(Long employeeId);

	Employee findEmployeeByEmail(String email);

	String getEmployeeEmailById(Long employeeId);

	Optional<EmployeeAccount> findByUsername(String username);
}
