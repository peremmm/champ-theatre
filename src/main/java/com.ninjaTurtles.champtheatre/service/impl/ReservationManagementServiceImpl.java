package com.ninjaTurtles.champtheatre.service.impl;

import com.ninjaTurtles.champtheatre.bean.ReservationBean;
import com.ninjaTurtles.champtheatre.models.Reservation;
import com.ninjaTurtles.champtheatre.repository.EmployeeRepository;
import com.ninjaTurtles.champtheatre.repository.ReservationRepository;
import com.ninjaTurtles.champtheatre.repository.TheatreRepository;
import com.ninjaTurtles.champtheatre.service.ReservationManagementService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;


public class ReservationManagementServiceImpl implements ReservationManagementService {
    private ReservationRepository reservationRepository;
    private EmployeeRepository employeeRepository;
    private TheatreRepository theatreRepository;

    @Autowired
    public ReservationManagementServiceImpl(ReservationRepository reservationRepository,EmployeeRepository employeeRepository, TheatreRepository theatreRepository) {
        this.reservationRepository = reservationRepository;
        this.employeeRepository = employeeRepository;
        this.theatreRepository = theatreRepository;
    }

    @Override
    public ReservationBean findById(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).get();
        return mapToReservationBean(reservation);
    }

    @Override
    public List<ReservationBean> searchReservationByBooker(Long bookerId) {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream().map((reservation) -> mapToReservationBean(reservation)).collect(Collectors.toList());
    }

    @Override
    public Reservation saveReservation(ReservationBean reservationBean) {
        return null;
    }

    @Override
    public void updateReservation(ReservationBean reservationBean) {

    }


    public Reservation mapToReservation(ReservationBean reservationBean) {
        return Reservation.builder()
                .id(reservationBean.getId())
                .event_description(reservationBean.getEvent_description())
                .event_type(reservationBean.getEvent_type())
                .eventDate(reservationBean.getEventDate())
                .startTime(reservationBean.getStartTime())
                .endTime(reservationBean.getEndTime())
                .theatre(reservationBean.getTheatre())
                .booker(reservationBean.getBooker())
                .reviewer(reservationBean.getReviewer())
                .participants(reservationBean.getParticipants())
                .status(reservationBean.getStatus())
                .build();

    }

    public ReservationBean mapToReservationBean(Reservation reservation) {
        return ReservationBean.builder()
                .id(reservation.getId())
                .event_description(reservation.getEvent_description())
                .event_type(reservation.getEvent_type())
                .eventDate(reservation.getEventDate())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .theatre(reservation.getTheatre())
                .booker(reservation.getBooker())
                .reviewer(reservation.getReviewer())
                .participants(reservation.getParticipants())
                .status(reservation.getStatus())
                .updateOn(reservation.getModifiedDate())
                .createdOn(reservation.getCreatedDate())
                .build();

    }
}
