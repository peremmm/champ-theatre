package com.ninjaTurtles.champtheatre.service.impl;

import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import com.ninjaTurtles.champtheatre.repository.EmployeeAccountRepository;
import com.ninjaTurtles.champtheatre.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private final EmployeeAccountRepository employeeAccountRepository;

    @Autowired
    public LoginServiceImpl(EmployeeAccountRepository employeeAccountRepository) {
        this.employeeAccountRepository = employeeAccountRepository;
    }

    @Override
    public void changePassword(String username, String newPassword) {
        Optional<EmployeeAccount> optionalEmployeeAccount = employeeAccountRepository.findByUsername(username);

        if (optionalEmployeeAccount.isPresent()) {
            EmployeeAccount employeeAccount = optionalEmployeeAccount.get();

            if (employeeAccount.getStatus() == EmployeeAccount.Status.ACTIVE) {
                if (isValidPassword(newPassword)) {
                    employeeAccount.setPassword(newPassword);
                } else {
                    throw new IllegalArgumentException("Invalid password. The password must contain at least one lowercase letter, one uppercase letter, and one number.");
                }
            } else {
                if (isValidPassword(newPassword)) {
                    employeeAccount.setPassword(newPassword);
                    employeeAccount.setStatus(EmployeeAccount.Status.ACTIVE);
                } else {
                    throw new IllegalArgumentException("Invalid password. The password must contain at least one lowercase letter, one uppercase letter, and one number.");
                }
            }

            employeeAccountRepository.save(employeeAccount);
        }
    }

    private boolean isValidPassword(String password) {
        boolean hasLowercase = false;
        boolean hasUppercase = false;
        boolean hasNumber = false;

        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            }

            if (hasLowercase && hasUppercase && hasNumber) {
                return true; // Password meets the requirements
            }
        }

        return false; // Password does not meet the requirements
    }


    @Override
    public boolean isPasswordChanged(String username) {
        Optional<EmployeeAccount> employeeAccountOptional = employeeAccountRepository.findByUsername(username);
        return employeeAccountOptional
                .map(employeeAccount -> employeeAccount.getStatus() != EmployeeAccount.Status.INACTIVE)
                .orElse(false);
    }

}
