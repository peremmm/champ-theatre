package com.ninjaTurtles.champtheatre.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRoleId implements Serializable {
    private static final long serialVersionUID = 2360992123657946697L;

    @Column(name = "employee_id")
    private Long employeeId;
    
    @Column(name = "role_id")
    private Long roleId;

}
