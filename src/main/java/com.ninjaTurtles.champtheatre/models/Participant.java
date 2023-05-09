package com.ninjaTurtles.champtheatre.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.EmbeddedId;
import javax.persistence.MapsId;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PARTICIPANTS")
public class Participant implements Serializable {
    private static final long serialVersionUID = 2638595951154555879L;


    @EmbeddedId
    private ParticipantId id;

    @ManyToOne
    @MapsId("reservationId")
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    @Column(columnDefinition = "VARCHAR(75)", nullable = false)
    private String firstName;

    @Column(columnDefinition = "VARCHAR(75)", nullable = false)
    private String lastName;
    
    @Column(columnDefinition = "VARCHAR(75) DEFAULT 'CHAMP'", nullable = true)
    private String company;

}


