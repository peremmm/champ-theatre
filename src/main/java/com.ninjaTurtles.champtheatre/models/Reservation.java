package com.ninjaTurtles.champtheatre.models;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "RESERVATIONS")
@Setter
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
   
    @Column( nullable = false)
    private Integer event_type;
    
    @Lob
    @Column(nullable = false)
    private String event_description;
    
    @Column(columnDefinition = "DATE", nullable = false)
    private Date eventDate;
    
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
    
    @OneToMany(mappedBy = "reservation")
    private Set<Participant> participants = new HashSet<>();
}
