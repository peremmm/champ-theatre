package com.ninjaTurtles.champtheatre.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = EmployeeRole.TABLE_NAME)
public class EmployeeRole {
    protected static final String TABLE_NAME = "employee_role";

    @EmbeddedId
    private EmployeeRoleId id;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

}
