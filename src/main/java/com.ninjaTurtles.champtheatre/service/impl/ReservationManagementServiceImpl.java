package com.ninjaTurtles.champtheatre.service.impl;

import com.ninjaTurtles.champtheatre.bean.ReservationBean;
import com.ninjaTurtles.champtheatre.exception.DataAccessException;
import com.ninjaTurtles.champtheatre.exception.ServiceException;
import com.ninjaTurtles.champtheatre.models.*;
import com.ninjaTurtles.champtheatre.repository.EmployeeRepository;
import com.ninjaTurtles.champtheatre.repository.ReservationRepository;
import com.ninjaTurtles.champtheatre.repository.TheatreRepository;
import com.ninjaTurtles.champtheatre.service.ReservationManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationManagementServiceImpl implements ReservationManagementService {

    public enum Modules {
        EmployeeAccountManagement,
        ReservationRequestManagement,
        ThreatreManagement,
        ReservationManagement,
        PasswordManagement,
        RoleManagement,

    }
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
    public List<ReservationBean> findByBooker(Employee booker) {
        Optional<Reservation> optionalReservation = reservationRepository.findByBooker(booker);
        if (optionalReservation.isPresent()){
            List<Reservation> reservations = optionalReservation.map(Collections::singletonList).orElse(Collections.emptyList());
            return reservations.stream().map((reservation) -> mapToReservationBean(reservation)).collect(Collectors.toList());

        }
        return Collections.emptyList();

    }

    @Override
    public List<ReservationBean> findByReviewer(Employee reviewer) {

        Optional<Reservation> optionalReservation = reservationRepository.findByReviewer(reviewer);
        if (optionalReservation.isPresent()){
            List<Reservation> reservations = optionalReservation.map(Collections::singletonList).orElse(Collections.emptyList());
            return reservations.stream().map((reservation) -> mapToReservationBean(reservation)).collect(Collectors.toList());

        }
        return Collections.emptyList();
    }

    /**  FOR TESTING ONLY**/
    private Employee getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    @Transactional
    @Override
    public void save(ReservationBean reservationBean) {
        Reservation newReservation = new Reservation();
        newReservation.setEvent_type(reservationBean.getEvent_type());
        newReservation.setEvent_description(reservationBean.getEvent_description());
        newReservation.setEventDate(reservationBean.getEventDate());
        newReservation.setStartTime(reservationBean.getStartTime());
        newReservation.setEndTime(reservationBean.getEndTime());
        newReservation.setStatus(Reservation.Status.UNREVIEWED);
        newReservation.setTheatre(reservationBean.getTheatre());
        newReservation.setAttendees(reservationBean.getAttendees());
        //NEEDS TO BE UPDATED TO USE USER SESSION
        //newReservation.setBooker(UsersessionId);
        //temporary
        newReservation.setBooker(getEmployee(201L));
        try {
            reservationRepository.save(newReservation);
        }catch (DataAccessException e){
            throw new ServiceException(e.getMessage());
        }
    }


    @Override
    public List<ReservationBean> findAll() {
        return reservationRepository.findAll().stream().map(this::mapToReservationBean).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateStatus(ReservationBean reservationBean) {

        Reservation existingReservation = reservationRepository.findById(reservationBean.getId()).orElseThrow(null);
        // When session is setup, use this for the if condition
        //if(existingReservation != null &&  reservationBean.getReviewer().getId() == userSessionId){
        if(existingReservation != null &&  reservationBean.getReviewer() != null) {
            Set<EmployeeRole> currentRoles = reservationBean.getReviewer().getRoles();

            for(EmployeeRole employeeRole : currentRoles){
                if(employeeRole.getRole().getModule().contains(Modules.ReservationRequestManagement)) {
                    if (existingReservation.getReviewer() == null) {
                        existingReservation.setStatus(Reservation.Status.PENDING);
                        existingReservation.setReviewer(reservationBean.getReviewer());

                    } //else if (existingReservation.getReviewer().getId() == userSessionId && existingReservation.getStatus() == Reservation.Status.PENDING) {
                    else if (existingReservation.getReviewer() != null && existingReservation.getStatus() == Reservation.Status.PENDING) {
                        existingReservation.setStatus(reservationBean.getStatus());
                    }
                    reservationRepository.save(existingReservation);
                    break;
                }
            }

        }else {
            throw new ServiceException("Something went wrong. We can't seem to find the reservation that you want to modify.");
        }
    }

    @Transactional
    @Override
    public void updateDetails(ReservationBean reservationBean) {
        // For testing only
        //Reservation existingReservation = reservationRepository.findById(1001L).orElseThrow(null);

        Reservation existingReservation = reservationRepository.findById(reservationBean.getId()).orElseThrow(null);
        if(existingReservation != null) {
            if (reservationBean.getBooker().getId().equals(existingReservation.getBooker().getId()) && existingReservation.getStatus() == Reservation.Status.UNREVIEWED) {
            //if (reservationBean.getBooker().getId().equals(userSessionId) && reservationBean.getBooker().getId().equals(existingReservation.getBooker().getId()) && existingReservation.getStatus() == Reservation.Status.UNREVIEWED) {
                existingReservation.setEventDate(reservationBean.getEventDate());
                existingReservation.setStartTime(reservationBean.getStartTime());
                existingReservation.setEndTime(reservationBean.getEndTime());
                existingReservation.setAttendees(reservationBean.getAttendees());
                existingReservation.setTheatre(reservationBean.getTheatre());
                existingReservation.setModifiedDate(new Date());
                reservationRepository.save(existingReservation);
            }
        } else {
            throw new ServiceException("Something went wrong. We can't seem to find the reservation that you want to modify.");
        }
    }


    private Reservation mapToReservation(ReservationBean reservationBean) {
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
                .attendees(reservationBean.getAttendees())
                .status(reservationBean.getStatus())
                .build();

    }

    private ReservationBean mapToReservationBean(Reservation reservation) {
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
                .attendees(reservation.getAttendees())
                .status(reservation.getStatus())
                .modifiedDate(reservation.getModifiedDate())
                .createdDate(reservation.getCreatedDate())
                .build();
    }
}
