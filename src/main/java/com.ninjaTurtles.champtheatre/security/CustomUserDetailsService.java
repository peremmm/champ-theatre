package com.ninjaTurtles.champtheatre.security;

import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import com.ninjaTurtles.champtheatre.repository.EmployeeAccountRepository;
import com.ninjaTurtles.champtheatre.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeAccountRepository employeeAccountRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public CustomUserDetailsService(EmployeeAccountRepository employeeAccountRepository,
                                    EmployeeRepository employeeRepository) {
        this.employeeAccountRepository = employeeAccountRepository;
        this.employeeRepository = employeeRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EmployeeAccount employeeAccount = employeeAccountRepository.findFirstByUsername(username);
        if(employeeAccount != null) {
            User authUser = new User(
                    employeeAccount.getEmployee().getEmail(),
                    employeeAccount.getPassword(),
                    employeeAccount.getEmployee().getRoles().stream()
                            .map((role) -> new SimpleGrantedAuthority(role.getRole().getRole()))
                            .collect(Collectors.toList())
            );
            return authUser;
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }
}
