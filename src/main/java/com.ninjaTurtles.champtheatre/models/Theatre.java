package com.ninjaTurtles.champtheatre.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "THEATERS")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Theatre extends AbstractEntity {

	public enum Status {
		AVAILABLE, OCCUPIED, MAINTENANCE
	}

	private static final long serialVersionUID = -127860149735454233L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_theatre_sequence")
	@SequenceGenerator(sequenceName = "theatre_sequence", name = "custom_theatre_sequence", allocationSize = 1)
	private Long id;

	@Column(columnDefinition = "VARCHAR(75)", nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(length = 15, nullable = false)
	private Status status;

	@Column(name = "capacity", columnDefinition = "SMALLINT", nullable = false)
	private Integer capacity;

	@OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Reservation> reservations = new ArrayList<>();

	@CreationTimestamp
	private Date createdDate;

	@UpdateTimestamp
	private Date modifiedDate;

}
