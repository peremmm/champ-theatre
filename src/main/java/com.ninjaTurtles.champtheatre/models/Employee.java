package com.ninjaTurtles.champtheatre.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EMPLOYEES")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Employee implements Serializable{

    private static final long serialVersionUID = -5800161177605867628L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_employee_sequence")
    @SequenceGenerator(sequenceName = "employee_sequence", name = "custom_employee_sequence", initialValue = 200, allocationSize = 1)
    @Column(name = "id")
    private Long id;


    @Column(columnDefinition = "VARCHAR(75)", nullable = false)
    private String firstName;

    @Column(columnDefinition = "VARCHAR(75)", nullable = false)
    private String lastName;

    @Column(columnDefinition = "VARCHAR(70)", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Participant> participants = new ArrayList<>();
    
    @OneToMany(mappedBy = "booker", cascade = CascadeType.ALL)
    private List<Reservation> reservations1 = new ArrayList<>();
    
    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL)
    private List<Reservation> reservations2 = new ArrayList<>();
    
    @OneToMany(mappedBy = "employee")
    private Set<EmployeeRole> roles = new HashSet<>();
    
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private EmployeeAccount employeeAccount;

    
}
