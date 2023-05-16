package com.ninjaTurtles.champtheatre.models;


import java.util.*;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "RESERVATIONS")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Reservation extends AbstractEntity {
    
    private static final long serialVersionUID = -644923194083178365L;
    
    public enum Status {
        UNREVIEWED,

        PENDING,

        APPROVED,
        CANCELLED,
        REJECTED,

    }
    public enum Type {
        BUSINESS,
        LEISURE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_reservation_sequence")
    @SequenceGenerator(sequenceName = "reservation_sequence", name = "custom_reservation_sequence", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private Type event_type;
    
    @Lob
    @Column(nullable = false)
    private String event_description;
    
    @Column(columnDefinition = "DATE", nullable = false)
    private Date eventDate;

    @Column(columnDefinition = "DATE", nullable = false)
    private Date startTime;

    @Column(columnDefinition = "DATE", nullable = false)
    private Date endTime;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private Status status;
    
    @ManyToOne(fetch = FetchType.EAGER, optional= false)
    private Theatre theatre;

    @ManyToOne(fetch = FetchType.EAGER, optional= false)
    @JoinColumn(name = "booker_id")
    private Employee booker;
    
    @ManyToOne(fetch = FetchType.EAGER, optional= true)
    @JoinColumn(name = "reviewer_id")
    private Employee reviewer;

    @Column(name = "attendees", columnDefinition = "SMALLINT", nullable = false)
    private Integer attendees;



}
