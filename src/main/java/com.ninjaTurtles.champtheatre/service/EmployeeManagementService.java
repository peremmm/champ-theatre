package com.ninjaTurtles.champtheatre.service;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeManagementService {
	
	void register(Employee employee);
	
	List<EmployeeBean> getAllEmployee();
	
	void updateEmployeeDetails(Employee employee);
	
	void updateEmployeeAccountStatus(EmployeeAccount employeeAccount);
	
	void deleteEmployeeAccount(EmployeeAccount employeeAccount);
	
	void addEmployeeAccount(Employee employeeId, EmployeeAccount employeeAccount);

}
