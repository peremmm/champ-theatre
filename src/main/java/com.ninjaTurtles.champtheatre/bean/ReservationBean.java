package com.ninjaTurtles.champtheatre.bean;

import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.Reservation;
import com.ninjaTurtles.champtheatre.models.Theatre;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@Builder
public class ReservationBean {

    private Long id;
    private Reservation.Type event_type;
    private String event_description;
    private Date eventDate;
    private Date startTime;
    private Date endTime;
    private Reservation.Status status;
    private Theatre theatre;
    private Employee booker;
    private Employee reviewer;
    private Integer attendees;

    @CreationTimestamp
    private Date modifiedDate;

    @CreationTimestamp
    private Date createdDate;

}
