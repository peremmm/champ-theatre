package com.ninjaTurtles.champtheatre.controller;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import com.ninjaTurtles.champtheatre.repository.EmployeeAccountRepository;
import com.ninjaTurtles.champtheatre.security.SecurityUtil;
import com.ninjaTurtles.champtheatre.service.EmployeeManagementService;
import com.ninjaTurtles.champtheatre.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model) {
        String username = SecurityUtil.getSessionUser();
        EmployeeAccount employeeBean = employeeAccountRepository.findByUsername(username).get();
        model.addAttribute("employee", employeeBean); // Update attribute name to "employee"
        return "change-password";
    }


    @PostMapping("/change-password")
    public String changePassword(@RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 RedirectAttributes redirectAttributes) {

        String username = SecurityUtil.getSessionUser();

        if (newPassword.equals(confirmPassword)) {
            try {
                loginService.changePassword(username, newPassword);
            } catch (IllegalArgumentException e){
                redirectAttributes.addFlashAttribute("error", e.getMessage());
                return "redirect:/change-password";
            }
            redirectAttributes.addFlashAttribute("message",
                    "Password for " + username + " has been changed");
        } else {
            // Passwords do not match
            redirectAttributes.addFlashAttribute("error",
                    "New password and confirm password do not match");
            return "redirect:/change-password";
        }
        return "redirect:/reservations";
    }

}
