package com.ninjaTurtles.champtheatre.service;

import com.ninjaTurtles.champtheatre.bean.ModuleBean;
import com.ninjaTurtles.champtheatre.bean.RoleBean;
import com.ninjaTurtles.champtheatre.models.EmployeeRole;
import com.ninjaTurtles.champtheatre.models.Module;
import com.ninjaTurtles.champtheatre.models.Role;
import com.ninjaTurtles.champtheatre.models.RoleModule;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface RoleManagementService {
	List<RoleBean> getAllRoles();

	Optional<Role> getRoleById(Long id);

	void addRole(RoleBean role, Set<ModuleBean> modules);
	
	void updateRole(Role role, Set<ModuleBean> modules);
	
	void deleteRole(Role role);
	
//	void updateEmployeeRole(EmployeeRole employeeRole);
	
}
