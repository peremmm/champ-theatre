package com.ninjaTurtles.champtheatre.service.impl;

import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import com.ninjaTurtles.champtheatre.repository.EmployeeAccountRepository;
import com.ninjaTurtles.champtheatre.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private final EmployeeAccountRepository employeeAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginServiceImpl(EmployeeAccountRepository employeeAccountRepository, PasswordEncoder passwordEncoder) {
        this.employeeAccountRepository = employeeAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean authenticate(String username, String password) {
        // Perform authentication logic, e.g., check against the database
        Optional<EmployeeAccount> optionalEmployeeAccount = employeeAccountRepository.findByUsername(username);

        if (optionalEmployeeAccount.isPresent()) {
            EmployeeAccount employeeAccount = optionalEmployeeAccount.get();
            return employeeAccount.getPassword().equals(password);
        }

        return false;
    }

    @Override
    public void changePassword(String username, String newPassword) {
        Optional<EmployeeAccount> optionalEmployeeAccount = employeeAccountRepository.findByUsername(username);

        if (optionalEmployeeAccount.isPresent()) {
            EmployeeAccount employeeAccount = optionalEmployeeAccount.get();

            if (employeeAccount.getStatus() == EmployeeAccount.Status.ACTIVE) {
                employeeAccount.setPassword(passwordEncoder.encode(newPassword));
            } else {
                employeeAccount.setPassword(passwordEncoder.encode(newPassword));
                employeeAccount.setStatus(EmployeeAccount.Status.ACTIVE);
            }

            employeeAccountRepository.save(employeeAccount);
        }
    }

    @Override
    public boolean isPasswordChanged(String username) {
        Optional<EmployeeAccount> employeeAccountOptional = employeeAccountRepository.findByUsername(username);
        return employeeAccountOptional
                .map(employeeAccount -> employeeAccount.getStatus() != EmployeeAccount.Status.INACTIVE)
                .orElse(false);
    }

}
