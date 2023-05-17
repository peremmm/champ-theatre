package com.ninjaTurtles.champtheatre.controller;

import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.repository.EmployeeRepository;
import com.ninjaTurtles.champtheatre.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public ForgotPasswordController(ForgotPasswordService forgotPasswordService, EmployeeRepository employeeRepository) {
        this.forgotPasswordService = forgotPasswordService;
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String sendResetPasswordEmail(@RequestParam("email") String email, Model model) {
        // Check if the email exists in the system
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        if (optionalEmployee.isPresent()) {
            forgotPasswordService.sendPasswordResetEmail(email);
            model.addAttribute("emailSent", true);
        } else {
            model.addAttribute("emailSent", false);
        }
        return "reset-password-email-sent";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String resetToken, Model model) {
        if (forgotPasswordService.isValidResetToken(resetToken)) {
            model.addAttribute("resetToken", resetToken);
            return "change-password";
        } else {
            // Invalid reset token
            return "error"; // Replace with desired error page
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String resetToken,
                                @RequestParam("newPassword") String newPassword,
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model) {
        if (!newPassword.equals(confirmPassword)) {
            // Passwords do not match
            model.addAttribute("resetToken", resetToken);
            model.addAttribute("errorMessage", "Passwords do not match");
            return "change-password";
        }

        if (forgotPasswordService.isValidResetToken(resetToken)) {
            forgotPasswordService.resetPassword(resetToken, newPassword);
            return "redirect:/login";
        } else {
            // Invalid reset token
            return "error"; // Replace with your desired error page
        }
    }
}
