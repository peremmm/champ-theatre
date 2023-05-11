package com.ninjaTurtles.champtheatre.bean;

import com.ninjaTurtles.champtheatre.models.Reservation;
import com.ninjaTurtles.champtheatre.models.Theatre;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class TheatreBean {
    private Long id;
    private String name;
    private Theatre.Status status;
    private Integer capacity;
    private List<Reservation> reservations;
    private Date createdDate;
    private Date modifiedDate;
}
