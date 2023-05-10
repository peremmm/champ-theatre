package com.ninjaTurtles.champtheatre.service;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EmployeeManagementService {

	List<EmployeeBean> getAllEmployee();

	Employee register(Employee employee);

	void updateEmployeeDetails(Employee employee);

	void updateEmployeeAccountStatus(EmployeeAccount employeeAccount);

	void deleteEmployeeAccount(EmployeeAccount employeeAccount);

	EmployeeAccount addEmployeeAccount(Long employeeId, EmployeeAccount employeeAccount);

}
