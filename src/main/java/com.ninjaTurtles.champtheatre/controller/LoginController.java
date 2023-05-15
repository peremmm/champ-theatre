package com.ninjaTurtles.champtheatre.controller;

import com.ninjaTurtles.champtheatre.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session) {

        if (loginService.authenticate(username, password)) {
            // Successful login
            session.setAttribute("username", username);

            if (loginService.isPasswordChanged(username)) {
                return "redirect:/theatre-list";
            } else {
                return "change-password";
            }
        } else {
            // Invalid credentials
            return "login";
        }
    }

    @GetMapping("/change-password")
    public String showPasswordChangeForm() {
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 HttpSession session) {

        String username = (String) session.getAttribute("username");

        if (newPassword.equals(confirmPassword)) {
            loginService.changePassword(username, newPassword);
            return "employees-list";
        } else {
            // Passwords do not match
            return "change-password";
        }
    }
}
