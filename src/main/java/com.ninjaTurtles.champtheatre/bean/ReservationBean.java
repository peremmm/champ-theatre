package com.ninjaTurtles.champtheatre.bean;

import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.Participant;
import com.ninjaTurtles.champtheatre.models.Reservation;
import com.ninjaTurtles.champtheatre.models.Theatre;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

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
    private List<Participant> participants;

    @CreationTimestamp
    private Date modifiedDate;

    @CreationTimestamp
    private Date createdDate;

}
