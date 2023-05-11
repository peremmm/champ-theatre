package com.ninjaTurtles.champtheatre.service;

import com.ninjaTurtles.champtheatre.bean.RoleBean;
import com.ninjaTurtles.champtheatre.models.EmployeeRole;
import com.ninjaTurtles.champtheatre.models.Module;
import com.ninjaTurtles.champtheatre.models.Role;
import com.ninjaTurtles.champtheatre.models.RoleModule;

import java.util.List;
import java.util.Set;


public interface RoleManagementService {
	
	List<RoleBean> getAllRoles();
	
	void addRole(Role role, Set<RoleModule> modules);
	
	void updateRole(Role role, Set<RoleModule> modules);
	
	void deleteRole(Role role);
	
//	void updateEmployeeRole(EmployeeRole employeeRole);
	
}
