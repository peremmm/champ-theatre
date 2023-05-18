package com.ninjaTurtles.champtheatre.security;

import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import com.ninjaTurtles.champtheatre.models.EmployeeAccount.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final EmployeeDetailsService employeeDetailsService;

    @Autowired
    public SecurityConfig(EmployeeDetailsService employeeDetailsService) {
        this.employeeDetailsService = employeeDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/forgot-password", "/employees/{employeeId}/change-password", "/css/**", "/js/**", "/img/**")
                .permitAll()
                .antMatchers("/employees/**", "/theatres/**", "/requests", "/roleManagement").hasRole("Administrator")
                .antMatchers("/requests/**").hasRole("RESERVATION COORDINATOR")
                .anyRequest().authenticated()
                .and()
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/reservations")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?error=true")
                        .permitAll()
                        .successHandler(new CustomAuthenticationSuccessHandler()) // Custom success handler
                        .failureHandler(new CustomAuthenticationFailureHandler()) // Custom failure handler
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
                );

        return http.build();
    }

    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(employeeDetailsService).passwordEncoder(passwordEncoder());
    }

    // Custom success handler
    public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            EmployeeAccount employeeAccount = getLoggedInEmployeeAccount(authentication);
            if (employeeAccount != null) {
                if (employeeAccount.getStatus() == Status.INACTIVE) {
                    // Handle inactive account by redirecting to change-password page
                    String employeeId = String.valueOf(employeeAccount.getEmployee().getId());
                    response.sendRedirect("/employees/" + employeeId + "/change-password");
                } else if (employeeAccount.getStatus() == Status.TERMINATED) {
                    // Handle terminated account
                    response.sendRedirect("/terminated-account");
                } else {
                    // Handle other successful login scenarios
                    response.sendRedirect("/reservations");
                }
            } else {
                // Handle invalid authentication
                response.sendRedirect("/login?error=true");
            }
        }

        private EmployeeAccount getLoggedInEmployeeAccount(Authentication authentication) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String username = userDetails.getUsername();
                // Retrieve the EmployeeAccount using the username
                Optional<EmployeeAccount> employeeAccount = employeeDetailsService.getEmployeeAccount(username);
                return employeeAccount.orElse(null);
            }
            return null;
        }
    }

    // Custom failure handler
    public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            // Handle authentication failure
            response.sendRedirect("/login?error=true");
        }
    }
}
