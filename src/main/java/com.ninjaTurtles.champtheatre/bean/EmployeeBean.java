package com.ninjaTurtles.champtheatre.bean;

import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import com.ninjaTurtles.champtheatre.models.Reservation;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EmployeeBean {
    private Long id;

    @NotNull
    @NotEmpty(message = "Please enter the First name")
    @ValidName(message = "Invalid first name")
    private String firstName;

    @NotNull
    @NotEmpty(message = "Please enter the Last name")
    @ValidName(message = "Invalid last name")
    private String lastName;

    @Email
    @NotEmpty(message = "Please enter the Email")
    @Size(max = 70, message = "Email must be no more than 70 characters")
    private String email;
    private List<Reservation> reservations1;
    private List<Reservation> reservations2;
    private Long employeeRoleSet;
    private EmployeeAccount employeeAccount;
    private LocalDateTime createdOn;
    private LocalDateTime updateOn;
}
