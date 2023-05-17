package com.ninjaTurtles.champtheatre.service;

import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import org.springframework.stereotype.Service;

public interface LoginService {

    boolean authenticate(String username, String password);

    void changePassword(String username, String newPassword);

    boolean isPasswordChanged(String username);
}
