package com.ninjaTurtles.champtheatre.controller;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.service.EmployeeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EmployeeManagementController {

    private final EmployeeManagementService employeeManagementService;

    @Autowired
    public EmployeeManagementController(EmployeeManagementService employeeManagementService){
        this.employeeManagementService = employeeManagementService;
    }

    @GetMapping("/employees")
    public String listEmployees(Model model){
        List<EmployeeBean> employees = employeeManagementService.getAllEmployee();
        model.addAttribute("employees", employees);
        return "employees-list";

    }
}
