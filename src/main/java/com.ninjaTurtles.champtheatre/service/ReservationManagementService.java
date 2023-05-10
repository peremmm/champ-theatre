package com.ninjaTurtles.champtheatre.service;

import com.ninjaTurtles.champtheatre.bean.ReservationBean;
import com.ninjaTurtles.champtheatre.models.Reservation;
import com.ninjaTurtles.champtheatre.models.Theatre;
import com.ninjaTurtles.champtheatre.repository.ReservationRepository;

import java.util.List;


public interface ReservationManagementService {
	
	ReservationBean findById(Long reservationId);

	List<ReservationBean> searchReservationByBooker(Long bookerId);

	Reservation saveReservation(ReservationBean reservationBean);

	void updateReservation(ReservationBean reservationBean);


}
