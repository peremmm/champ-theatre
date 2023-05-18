package com.ninjaTurtles.champtheatre.service.impl;

import com.ninjaTurtles.champtheatre.bean.ModuleBean;
import com.ninjaTurtles.champtheatre.bean.RoleBean;
import com.ninjaTurtles.champtheatre.models.Module;
import com.ninjaTurtles.champtheatre.models.Role;
import com.ninjaTurtles.champtheatre.models.RoleModule;
import com.ninjaTurtles.champtheatre.models.RoleModuleId;
import com.ninjaTurtles.champtheatre.repository.RoleRepository;
import com.ninjaTurtles.champtheatre.service.RoleManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
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

    private RoleBean mapToRoleBean(Role role) {
        return RoleBean.builder()
                .id(role.getId())
                .role(role.getRole())
                .employee(role.getEmployee())
                .description(role.getDescription())
                .module(role.getModule().stream()
                        .map(roleModule -> {
                            ModuleBean moduleBean = new ModuleBean();
                            moduleBean.setModuleName(roleModule.getModule().getModule());
                            return moduleBean;
                        })
                        .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

//    @Override
//    public void addRole(RoleBean role, Set<ModuleBean> modules) {
//        // Convert RoleBean to Role
//        Role convertedRole = mapToRole(role);
//
//        // Set the modules for the role
//        Set<RoleModule> roleModules = modules.stream()
//                .map(moduleBean -> {
//                    Module module = new Module();
//                    module.setModule(moduleBean.getModuleName().toString());
//
//                    RoleModuleId roleModuleId = new RoleModuleId(convertedRole.getId(), module.getId());
//                    RoleModule roleModule = new RoleModule();
//                    roleModule.setId(roleModuleId);
//                    roleModule.setRole(convertedRole);
//                    roleModule.setModule(module);
//                    return roleModule;
//                })
//                .collect(Collectors.toSet());
//
//        convertedRole.setModule(roleModules);
//
//        // Save the role in the repository
//        roleRepository.save(convertedRole);
//    }

    @Override
    public void addRole(RoleBean roleBean, Set<ModuleBean> modules) {
        Role role = mapToRole(roleBean);

        Set<RoleModule> roleModules = modules.stream()
                .map(moduleBean -> {
                    Module module = new Module();
                    module.setModule(moduleBean.getModuleName());

                    RoleModule roleModule = new RoleModule();
                    roleModule.setRole(role);
                    roleModule.setModule(module);
                    return roleModule;
                })
                .collect(Collectors.toSet());

        role.setModule(roleModules);

        roleRepository.save(role);
    }

    private Role mapToRole(RoleBean roleBean) {
        Role role = new Role();
        role.setId(roleBean.getId());
        role.setRole(roleBean.getRole());
        role.setEmployee(roleBean.getEmployee());
        role.setDescription(roleBean.getDescription());

        // Perform any additional mappings for other properties

        return role;
    }



    @Override
    public void updateRole(Role role, Set<ModuleBean> modules) {
        // Set the modules for the role
//        role.setModule(modules);

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



