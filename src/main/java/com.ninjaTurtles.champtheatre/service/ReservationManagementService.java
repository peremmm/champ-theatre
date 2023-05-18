package com.ninjaTurtles.champtheatre.service;

import com.ninjaTurtles.champtheatre.bean.ReservationBean;
import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.Reservation;
import com.ninjaTurtles.champtheatre.models.Theatre;
import com.ninjaTurtles.champtheatre.repository.ReservationRepository;

import java.util.List;


public interface ReservationManagementService {
	
	ReservationBean findById(Long reservationId);

	List<ReservationBean> findByBooker(Employee booker);

	void save(ReservationBean reservationBean);

	void assign(Long reservationId);
	List<ReservationBean> findAll();

	void updateDetails(ReservationBean reservationbean);


    void updateStatus(Long reservationId, Reservation.Status status);
	void cancel(Long reservationId);

}
