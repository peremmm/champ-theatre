package com.ninjaTurtles.champtheatre.models;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantId implements Serializable {


    private static final long serialVersionUID = -197564728129721683L;
    
    @Column(name = "reservation_id")
    private Long reservationId;

    
	@Column(name = "employee_id")
    private Long employeeId;
    

}
