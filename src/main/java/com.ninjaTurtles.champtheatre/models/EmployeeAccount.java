package com.ninjaTurtles.champtheatre.models;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "EMPLOYEE_ACCOUNTS")
public class EmployeeAccount extends AbstractEntity{

    public EmployeeAccount(Long employeeId, String username, String password, boolean isPasswordChanged) {

        this.username = username;
        this.password = password;
        this.status = Status.INACTIVE;
    }

    public enum Status {
        ACTIVE,
        INACTIVE,
        TERMINATED
    }

    
    private static final long serialVersionUID = -1574715895034102724L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_employee_account_sequence")
    @SequenceGenerator(sequenceName = "employee_account_sequence", name = "custom_employee_account_sequence", initialValue = 1005, allocationSize = 1)
    private Long id;

	@Column(columnDefinition = "VARCHAR(75)", nullable = false)
    private String username;

    @Column(columnDefinition = "VARCHAR(75)", nullable = false)
    private String password;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private Status status;

    @Column(columnDefinition = "VARCHAR(255)")
    private String resetToken;

}