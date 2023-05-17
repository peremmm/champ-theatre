package com.ninjaTurtles.champtheatre.service.impl;

import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import com.ninjaTurtles.champtheatre.repository.EmployeeAccountRepository;
import com.ninjaTurtles.champtheatre.repository.EmployeeRepository;
import com.ninjaTurtles.champtheatre.service.EmailSenderService;
import com.ninjaTurtles.champtheatre.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final EmployeeAccountRepository employeeAccountRepository;
    private final EmailSenderService emailSenderService;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public ForgotPasswordServiceImpl(EmployeeAccountRepository employeeAccountRepository, EmailSenderService emailSenderService, EmployeeRepository employeeRepository) {
        this.employeeAccountRepository = employeeAccountRepository;
        this.emailSenderService = emailSenderService;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void sendPasswordResetEmail(String email) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        if (optionalEmployee.isPresent()) {
            Optional<EmployeeAccount> optionalEmployeeAccount = employeeAccountRepository.findByEmployeeId(optionalEmployee.get().getId());
            if (optionalEmployeeAccount.isPresent()) {
                EmployeeAccount employeeAccount = optionalEmployeeAccount.get();
                String resetToken = generateResetToken();

                // Save the reset token to the employee account for verification later
                employeeAccount.setResetToken(resetToken);
                employeeAccountRepository.save(employeeAccount);

                String resetLink = generateResetLink(resetToken);

                // Send the reset link to the user's email
                String emailContent = "Click the link below to reset your password:\n" + resetLink;
                emailSenderService.sendEmail(email, "Password Reset", emailContent);
            }
        }
    }

    @Override
    public String generateResetToken() {
        // Generate a random alphanumeric string as the reset token
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int tokenLength = 20; // Length of the reset token
        StringBuilder tokenBuilder = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < tokenLength; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            tokenBuilder.append(randomChar);
        }

        return tokenBuilder.toString();
    }

    @Override
    public String generateResetLink(String resetToken) {
        String resetLink = "http://localhost:8080/forgot-password?token=" + resetToken;
        return resetLink;
    }

    @Override
    public void resetPassword(String resetToken, String newPassword) {
        Optional<EmployeeAccount> optionalEmployeeAccount = employeeAccountRepository.findByResetToken(resetToken);
        if (optionalEmployeeAccount.isPresent()) {
            EmployeeAccount employeeAccount = optionalEmployeeAccount.get();
            employeeAccount.setPassword(newPassword);
            employeeAccount.setResetToken(null); // Reset the token after successful password change
            employeeAccountRepository.save(employeeAccount);
        }
    }

    @Override
    public boolean isValidResetToken(String resetToken) {
        // Check if the reset token exists in the database
        Optional<EmployeeAccount> optionalEmployeeAccount = employeeAccountRepository.findByResetToken(resetToken);
        return optionalEmployeeAccount.isPresent();
    }

}
