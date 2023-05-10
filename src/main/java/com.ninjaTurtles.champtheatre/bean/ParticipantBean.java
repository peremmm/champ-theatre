package com.ninjaTurtles.champtheatre.bean;

import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.Reservation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParticipantBean {
    private Long id;
    private Reservation reservation;
    private Employee employee;
    private String firstName;
    private String lastName;
    private String company;
}
