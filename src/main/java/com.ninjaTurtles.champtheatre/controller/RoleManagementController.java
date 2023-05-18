package com.ninjaTurtles.champtheatre.controller;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.bean.ModuleBean;
import com.ninjaTurtles.champtheatre.bean.RoleBean;
import com.ninjaTurtles.champtheatre.models.Module;
import com.ninjaTurtles.champtheatre.models.Role;
import com.ninjaTurtles.champtheatre.models.RoleModule;
import com.ninjaTurtles.champtheatre.service.EmployeeManagementService;
import com.ninjaTurtles.champtheatre.service.RoleManagementService;
import com.ninjaTurtles.champtheatre.service.impl.ReservationManagementServiceImpl.Modules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class RoleManagementController {
    private RoleManagementService roleManagementService;
    private EmployeeManagementService employeeManagementService;

    @Autowired
    public RoleManagementController(RoleManagementService roleManagementService, EmployeeManagementService employeeManagementService){
        this.roleManagementService = roleManagementService;
        this.employeeManagementService = employeeManagementService;
    }

    @GetMapping("/roleManagement")
    public String listRoles(Model model){
        List<RoleBean> roles = roleManagementService.getAllRoles();
        model.addAttribute("roles", roles);

        model.addAttribute("availableModules", Modules.values());
        return "role-management";
    }

    @GetMapping("/existingRoles")
    public String listExistingRoles(Model model) {
        List<RoleBean> roles = roleManagementService.getAllRoles();
        model.addAttribute("roles", roles);
        return "role-create";
    }

    @PostMapping("/createRole")
    public String createRole(@RequestParam("name") String roleName,
                             @RequestParam(value = "description", required = false) String roleDescription,
                             @RequestParam(value = "modules", required = true) List<String> moduleNames,
                             Model model) {
        RoleBean role = new RoleBean();
        role.setRole(roleName);
        role.setDescription(roleDescription);

        if (moduleNames != null) {
            Set<ModuleBean> modules = moduleNames.stream()
                    .map(moduleName -> {
                        ModuleBean module = new ModuleBean();
                        module.setModuleName(moduleName);
                        return module;
                    })
                    .collect(Collectors.toSet());
            roleManagementService.addRole(role, modules);
        } else {
            roleManagementService.addRole(role, Collections.emptySet());
        }

        model.addAttribute("role", role);
        return "redirect:/existingRoles";
    }


}
