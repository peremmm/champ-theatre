package com.ninjaTurtles.champtheatre.service;

import com.ninjaTurtles.champtheatre.models.EmployeeRole;
import com.ninjaTurtles.champtheatre.models.Module;
import com.ninjaTurtles.champtheatre.models.Role;

import java.util.List;


public interface RoleManagementService {
	
	List<Role> getAllRoles();
	
	void addRole(Role role, List<Module> modules);
	
	void updateRole(Role role, List<Module> modules);
	
	void deleteRole(Role role);
	
	void updateEmployeeRole(EmployeeRole employeeRole);
	
}
