package com.ninjaTurtles.champtheatre.security;

import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import com.ninjaTurtles.champtheatre.models.EmployeeRole;
import com.ninjaTurtles.champtheatre.repository.EmployeeAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    private final EmployeeAccountRepository employeeAccountRepository;

    @Autowired
    public EmployeeDetailsService(EmployeeAccountRepository employeeAccountRepository) {
        this.employeeAccountRepository = employeeAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EmployeeAccount employeeAccount = employeeAccountRepository.findFirstByUsername(username);
        if (employeeAccount != null) {
//            List<GrantedAuthority> authorities = new ArrayList<>();
//            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            User authUser = new User(
                    employeeAccount.getUsername(),
                    employeeAccount.getPassword(),
                    employeeAccount.getEmployee().getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getRole().getRole()))
                            .collect(Collectors.toList())
            );
            return authUser;
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    public Optional<EmployeeAccount> getEmployeeAccount(String username) {
        return employeeAccountRepository.findByUsername(username);
    }
}
