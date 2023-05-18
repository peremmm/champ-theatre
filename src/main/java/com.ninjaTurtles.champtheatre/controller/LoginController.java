package com.ninjaTurtles.champtheatre.controller;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.repository.EmployeeAccountRepository;
import com.ninjaTurtles.champtheatre.service.EmployeeManagementService;
import com.ninjaTurtles.champtheatre.service.LoginService;
import com.ninjaTurtles.champtheatre.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final LoginService loginService;
    private final EmployeeManagementService employeeManagementService;
    private final EmployeeAccountRepository employeeAccountRepository;

    @Autowired
    public LoginController(LoginService loginService, EmployeeManagementService employeeManagementService, EmployeeAccountRepository employeeAccountRepository) {
        this.loginService = loginService;
        this.employeeManagementService = employeeManagementService;
        this.employeeAccountRepository = employeeAccountRepository;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/employees/{employeeId}/change-password")
    public String showPasswordChangeForm(@PathVariable("employeeId") Long employeeId, Model model) {
        String username = SecurityUtil.getSessionUser();
        EmployeeBean employeeBean = employeeManagementService.findEmployeeById(employeeId);
        model.addAttribute("employeeBean", employeeBean);
        return "change-password";
    }

    @PostMapping("/employees/{employeeId}/change-password")
    public String changePassword(@PathVariable("employeeId") Long employeeId,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 RedirectAttributes redirectAttributes) {

        String username = SecurityUtil.getSessionUser();

        if (newPassword.equals(confirmPassword)) {
            loginService.changePassword(username, newPassword);
        }else {
            // Passwords do not match
            return "change-password";
        }

        redirectAttributes.addFlashAttribute("message", "Password for " + username + " has been changed");
        return "redirect:/employees";
    }
}
