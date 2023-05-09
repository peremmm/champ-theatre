package com.ninjaTurtles.champtheatre.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "THEATERS")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class Theatre extends AbstractEntity {

	public enum Status {
		AVAILABLE, OCCUPIED, MAINTENANCE
	}

	private static final long serialVersionUID = -127860149735454233L;

	@Column(columnDefinition = "VARCHAR(75)", nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(length = 15, nullable = false)
	private Status status;

	@Column(name = "capacity", columnDefinition = "SMALLINT", nullable = false)
	private Integer capacity;

	@OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Reservation> reservations = new ArrayList<>();

}
