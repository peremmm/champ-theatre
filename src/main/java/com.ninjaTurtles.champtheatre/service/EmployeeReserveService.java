package com.ninjaTurtles.champtheatre.service;

import com.ninjaTurtles.champtheatre.models.Participant;
import com.ninjaTurtles.champtheatre.models.Reservation;

import java.util.List;


public interface EmployeeReserveService {

	List<Reservation> getAllReserve();
	
	//??
	List<Participant> getParticipantsPerReserve();
	
	void newReservation(Reservation reservation);
	
	void updateReservation(Reservation reservation);
	
	//??
	void updateParticipants(Participant participant);

}
