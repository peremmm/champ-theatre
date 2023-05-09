package com.ninjaTurtles.champtheatre.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table(name = "EMPLOYEE_ACCOUNTS")
public class EmployeeAccount extends AbstractEntity{
	
	private enum Status {
        ACTIVE,
        INACTIVE,
        TERMINATED
    }

    
    private static final long serialVersionUID = -1574715895034102724L;

	@Column(columnDefinition = "VARCHAR(15)", nullable = false)
    private String username;

    @Column(columnDefinition = "VARCHAR(15)", nullable = false)
    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private Status status;
    
    
}