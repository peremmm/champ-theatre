package com.ninjaTurtles.champtheatre.controller;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.exception.ServiceException;
import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import com.ninjaTurtles.champtheatre.service.EmployeeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class EmployeeManagementController {

    private final EmployeeManagementService employeeManagementService;

    @Autowired
    public EmployeeManagementController(EmployeeManagementService employeeManagementService) {
        this.employeeManagementService = employeeManagementService;

    }

    @GetMapping("/employees")
    public String listEmployees(Model model, RedirectAttributes redirectAttributes,
                                @RequestParam(defaultValue = "id") String sortField,
                                @RequestParam(defaultValue = "asc") String sortOrder) {
        List<EmployeeBean> employees = employeeManagementService.getAllEmployee(sortField, sortOrder.equals("asc"));
        model.addAttribute("employees", employees);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortOrder", sortOrder);

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
    public String saveEmployee(@Valid @ModelAttribute("employee") EmployeeBean employeeBean,
                               BindingResult result,
                               RedirectAttributes redirectAttributes){

        if( result.hasErrors()) {
            return "employees-create";
        }
        EmployeeAccount employeeAccount = new EmployeeAccount();

        try {
            employeeManagementService.register(employeeBean);
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("error", "Email already exists");
            return "redirect:/employees/new";
        }
        employeeManagementService.addEmployeeAccount(employeeAccount, employeeBean);
//        EmployeeBean employeeId = employeeManagementService.findEmployeeIdByEmail(employee.getEmail());
//        Role role = new Role();
//        role.setId(103L);
//        employeeManagementService.addEmployeeRole(role.getId(), employeeId.getId());

        redirectAttributes.addFlashAttribute("message",
                "Employee " +
                        employeeBean.getFirstName() + " " +
                        employeeBean.getLastName() +
                        " has been registered successfully");

        return "redirect:/employees";
    }


    @GetMapping("/employees/{employeeId}/edit")
    public String editEmployeeForm(@PathVariable("employeeId") long employeeId, Model model){
        EmployeeBean employee = employeeManagementService.findEmployeeById(employeeId);
        model.addAttribute("employee", employee);
        return "employees-edit";
    }

    @PostMapping("/employees/{employeeId}/edit")
    public String updateEmployee(@PathVariable("employeeId") Long employeeId,
                                 @Valid @ModelAttribute("employee") EmployeeBean employeeBean,
                                 BindingResult result){
        if(result.hasErrors()){
            return "employees-edit";
        }
        employeeBean.setId(employeeId);
        employeeManagementService.updateEmployee(employeeBean);
        return "redirect:/employees";
    }


}
