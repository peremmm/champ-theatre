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

	List<ReservationBean> findByReviewer(Employee reviewer);

	void save(ReservationBean reservationBean);


	List<ReservationBean> findAll();

	void updateDetails(ReservationBean reservationbean);

	void updateStatus(ReservationBean reservationbean);
}
