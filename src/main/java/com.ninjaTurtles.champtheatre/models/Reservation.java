package com.ninjaTurtles.champtheatre.models;


import java.util.*;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "RESERVATIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends AbstractEntity {
    
    private static final long serialVersionUID = -644923194083178365L;
    
    public enum Status {
        PENDING,
        APPROVED,
        CANCELLED,
        REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_reservation_sequence")
    @SequenceGenerator(sequenceName = "reservation_sequence", name = "custom_reservation_sequence", allocationSize = 1)
    private Long id;

    @Column( nullable = false)
    private Integer event_type;
    
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

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants = new ArrayList<>();
}
