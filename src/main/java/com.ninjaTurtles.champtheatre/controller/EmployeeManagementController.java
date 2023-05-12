package com.ninjaTurtles.champtheatre.controller;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.exception.ServiceException;
import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import com.ninjaTurtles.champtheatre.service.EmployeeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class EmployeeManagementController {

    private final EmployeeManagementService employeeManagementService;

    @Autowired
    public EmployeeManagementController(EmployeeManagementService employeeManagementService) {
        this.employeeManagementService = employeeManagementService;

    }

    @GetMapping("/employees")
    public String listEmployees(Model model, RedirectAttributes redirectAttributes) {
        List<EmployeeBean> employees = employeeManagementService.getAllEmployee();
        model.addAttribute("employees", employees);

        if (redirectAttributes.containsAttribute("message")) {
            model.addAttribute("message", redirectAttributes.getAttribute("message"));
        }
        if (redirectAttributes.containsAttribute("error")) {
            model.addAttribute("error", redirectAttributes.getAttribute("error"));
        }

        return "employees-list";
    }

    @GetMapping("/employees/new")
    public String createEmployeeForm(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "employees-create";
    }

    @PostMapping("/employees/new")
    public String saveEmployee(@ModelAttribute("employee") Employee employee, RedirectAttributes redirectAttributes){
        try {
            employeeManagementService.register(employee);
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("error", "Email already exists");
            return "redirect:/employees/new"; // Redirect to the new employee form
        }

        EmployeeAccount employeeAccount = new EmployeeAccount();
        employeeManagementService.addEmployeeAccount(employeeAccount, employee);
        String message = "Employee " + employee.getFirstName() + " " + employee.getLastName() + " has been registered successfully";
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/employees";
    }


    @GetMapping("/employees/{employeeId}/edit")
    public String editEmployeeForm(@PathVariable("employeeId") long employeeId, Model model){
        EmployeeBean employee = employeeManagementService.findEmployeeById(employeeId);
        model.addAttribute("employee", employee);
        return "clubs-edit";
    }

    @PostMapping("/employees/{employeeId}/edit")
    public String updateEmployeeName(@PathVariable("employeeId") Long employeeId, @ModelAttribute("employee") EmployeeBean employeeBean){
        employeeBean.setId(employeeId);
        employeeManagementService.updateEmployeeId(employeeBean);
        return null;
    }


}
