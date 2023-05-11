package com.ninjaTurtles.champtheatre.controller;

import com.ninjaTurtles.champtheatre.bean.RoleBean;
import com.ninjaTurtles.champtheatre.service.RoleManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class RoleManagementController {
    private RoleManagementService roleManagementService;

    @Autowired
    public RoleManagementController(RoleManagementService roleManagementService){
        this.roleManagementService = roleManagementService;
    }

    @GetMapping("/roleManagement")
    public String listRoles(Model model){
        List<RoleBean> roles = roleManagementService.getAllRoles();
        model.addAttribute("roles", roles);
        return "role-management";
    }
}
