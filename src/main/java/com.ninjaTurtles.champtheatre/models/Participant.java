package com.ninjaTurtles.champtheatre.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(name = "PARTICIPANTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participant implements Serializable {


    private static final long serialVersionUID = 5702346475720618613L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_participant_sequence")
    @SequenceGenerator(sequenceName = "participant_sequence", name = "custom_participant_sequence", allocationSize = 1)
    private Long id;


    @Column(columnDefinition = "VARCHAR(75)", nullable = false)
    private String firstName;

    @Column(columnDefinition = "VARCHAR(75)", nullable = false)
    private String lastName;

    @ManyToOne(fetch = FetchType.EAGER, optional= false)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.EAGER,  optional= true)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(columnDefinition = "VARCHAR(75) DEFAULT 'CHAMP'", nullable = true)
    private String company;

}