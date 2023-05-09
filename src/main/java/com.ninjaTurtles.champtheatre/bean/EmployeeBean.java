package com.ninjaTurtles.champtheatre.bean;

import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import com.ninjaTurtles.champtheatre.models.Participant;
import com.ninjaTurtles.champtheatre.models.Reservation;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EmployeeBean {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Participant> participants;
    private List<Reservation> reservations1;
    private List<Reservation> reservations2;
    private EmployeeAccount employeeAccount;
    private LocalDateTime createdOn;
    private LocalDateTime updateOn;
}
