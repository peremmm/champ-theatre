package com.ninjaTurtles.champtheatre.service.impl;

import com.ninjaTurtles.champtheatre.bean.RoleBean;
import com.ninjaTurtles.champtheatre.models.EmployeeRole;
import com.ninjaTurtles.champtheatre.models.Role;
import com.ninjaTurtles.champtheatre.models.RoleModule;
import com.ninjaTurtles.champtheatre.repository.RoleRepository;
import com.ninjaTurtles.champtheatre.service.RoleManagementService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleManagementServiceImpl implements RoleManagementService {
    final RoleRepository roleRepository;

    @Autowired
    public RoleManagementServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleBean> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(this::mapToRoleBean).collect(Collectors.toList());
    }

    private RoleBean mapToRoleBean(Role role){
        return RoleBean.builder()
                .id(role.getId())
                .role(role.getRole())
                .employee(role.getEmployee())
                .module(role.getModule())
                .build();
    }

    @Override
    public void addRole(Role role, Set<RoleModule> modules) {
        // Set the modules for the role
        role.setModule(modules);

        // Save the role in the repository
        roleRepository.save(role);

        // Perform any additional logic or actions as needed
        // Example: trigger some event, update a cache, etc.
    }

    @Override
    public void updateRole(Role role, Set<RoleModule> modules) {
        // Set the modules for the role
        role.setModule(modules);

        // Save the role in the repository
        roleRepository.save(role);

        // Perform any additional logic or actions as needed
        // Example: trigger some event, update a cache, etc.
    }

    @Override
    public void deleteRole(Role role) {
        // Delete the role from the repository
        roleRepository.delete(role);

        // Perform any additional logic or actions as needed
        // Example: trigger some event, update a cache, etc.

    }

//    @Override
//    public void updateEmployeeRole(EmployeeRole employeeRole) {
//        // Find the existing role associated with the employee
//        Role existingRole = (Role) roleRepository.findAllByEmployee(employeeRole.getEmployee());
//
//        if (existingRole != null) {
//            // Update the existing role with the new employee role data
//            existingRole.setModule(employeeRole.getRole().getModule());
//
//            // Save the updated role in the repository
//            roleRepository.save(existingRole);
//        }
//        // Perform any additional logic or actions as needed
//        // Example: trigger some event, update a cache, etc.
//    }
}
