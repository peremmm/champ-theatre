package com.ninjaTurtles.champtheatre.service.impl;

import com.ninjaTurtles.champtheatre.bean.ReservationBean;
import com.ninjaTurtles.champtheatre.exception.DataAccessException;
import com.ninjaTurtles.champtheatre.exception.ServiceException;
import com.ninjaTurtles.champtheatre.models.*;
import com.ninjaTurtles.champtheatre.repository.EmployeeAccountRepository;
import com.ninjaTurtles.champtheatre.repository.EmployeeRepository;
import com.ninjaTurtles.champtheatre.repository.ReservationRepository;
import com.ninjaTurtles.champtheatre.security.SecurityUtil;
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
        RoleManagement,

    }

    private final ReservationRepository reservationRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeAccountRepository employeeAccountRepository;

    @Autowired
    public ReservationManagementServiceImpl(ReservationRepository reservationRepository, EmployeeRepository employeeRepository, EmployeeAccountRepository employeeAccountRepository) {
        this.reservationRepository = reservationRepository;
        this.employeeRepository = employeeRepository;
        this.employeeAccountRepository = employeeAccountRepository;
    }

    @Override
    public ReservationBean findById(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).get();
        return mapToReservationBean(reservation);
    }

    @Override
    public List<ReservationBean> findByUser() {
        String username = SecurityUtil.getSessionUser();
        EmployeeAccount employeeAccount = employeeAccountRepository.findByUsername(username).get();
        Optional<Reservation> optionalReservation = reservationRepository.findByBooker(employeeAccount.getEmployee());
        if (optionalReservation.isPresent()) {
            List<Reservation> reservations = optionalReservation.map(Collections::singletonList).orElse(Collections.emptyList());
            return reservations.stream().map((reservation) -> mapToReservationBean(reservation)).collect(Collectors.toList());
        }
        return Collections.emptyList();

    }


    /**
     * FOR TESTING ONLY
     **/
//    private Employee getEmployee(Long employeeId) {
//        return employeeRepository.findById(employeeId).get();
//    }

    @Transactional
    @Override
    public void save(ReservationBean reservationBean) {
        boolean doesNotCollide = true;

        List<Reservation> existingReservations = reservationRepository.findAll().stream()
                .filter(reservation -> reservation.getStatus().equals(Reservation.Status.APPROVED) ||
                        reservation.getStatus().equals(Reservation.Status.UNREVIEWED))
                .collect(Collectors.toList());

        for (Reservation reservation : existingReservations) {
            if (reservationBean.getEndTime().after(reservation.getStartTime()) &&
                    reservationBean.getStartTime().before(reservation.getEndTime())) {
                doesNotCollide = false;
                break;
            }
        }

        if (doesNotCollide) {
            Reservation newReservation = new Reservation();
            newReservation.setEvent_type(reservationBean.getEvent_type());
            newReservation.setEvent_description(reservationBean.getEvent_description());
            newReservation.setEventDate(reservationBean.getEventDate());
            newReservation.setStartTime(reservationBean.getStartTime());
            newReservation.setEndTime(reservationBean.getEndTime());
            newReservation.setTheatre(reservationBean.getTheatre());

            if(reservationBean.getTheatre().getCapacity() >= reservationBean.getAttendees()){
                newReservation.setAttendees(reservationBean.getAttendees());
            }else{
                throw new ServiceException("Reservation's attendees exceeded the theatre's capacity.");
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(reservationBean.getEventDate());
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            boolean isWeekend = dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;

            if (isWeekend) {
                newReservation.setStatus(Reservation.Status.UNREVIEWED);
            } else {
                newReservation.setStatus(Reservation.Status.APPROVED);
            }

            String username = SecurityUtil.getSessionUser();
            EmployeeAccount employeeAccount = employeeAccountRepository.findByUsername(username).get();
            newReservation.setBooker(employeeAccount.getEmployee());

            try {
                reservationRepository.save(newReservation);
            } catch (DataAccessException e) {
                throw new ServiceException("Something went wrong. Please try again.");
            }
        } else {
            throw new ServiceException("Conflict with existing reservation/s.");
        }

    }

    @Transactional
    @Override
    public void assign(Long reservationId) {
        Reservation existingReservation = reservationRepository.findById(reservationId).orElseThrow(null);

        if (existingReservation != null && existingReservation.getReviewer() == null) {
            String username = SecurityUtil.getSessionUser();
            EmployeeAccount employeeAccount = employeeAccountRepository.findByUsername(username).get();
            Employee reviewer = employeeAccount.getEmployee();
            Set<EmployeeRole> currentRoles = reviewer.getRoles();
            if (!currentRoles.isEmpty()) {
                for (EmployeeRole employeeRole : currentRoles) {
                    for (RoleModule roleModule : employeeRole.getRole().getModule()) {
                        if (roleModule.getModule().getModule().equals("ReservationRequestManagement")) {
                            existingReservation.setStatus(Reservation.Status.PENDING);
                            existingReservation.setReviewer(reviewer);
                            reservationRepository.save(existingReservation);
                            break;
                        }
                    }
                }
            } else {
                throw new ServiceException("Employee does not have a role!");
            }

        } else {
            throw new ServiceException("Something went wrong. We cannot find the reservation");
        }
    }


    @Override
    public List<ReservationBean> findAll() {
        return reservationRepository.findAll().stream().map(this::mapToReservationBean).collect(Collectors.toList());
    }


    @Transactional
    @Override
    public void updateStatus(Long reservationId, Reservation.Status status) {

        Reservation existingReservation = reservationRepository.findById(reservationId).orElseThrow(null);

        if (existingReservation != null && existingReservation.getReviewer() != null && (status == Reservation.Status.APPROVED || status == Reservation.Status.REJECTED)) {

            String username = SecurityUtil.getSessionUser();
            EmployeeAccount employeeAccount = employeeAccountRepository.findByUsername(username).get();
            Employee reviewer = employeeAccount.getEmployee();
            Set<EmployeeRole> currentRoles = reviewer.getRoles();

            if (!currentRoles.isEmpty()) {
                for (EmployeeRole employeeRole : currentRoles) {
                    for (RoleModule roleModule : employeeRole.getRole().getModule()) {
                        if (roleModule.getModule().getModule().equals("ReservationRequestManagement")) {
                            existingReservation.setStatus(status);
                            reservationRepository.save(existingReservation);
                            break;
                        }
                    }
                }
            }

            //}else {
            //  throw new ServiceException("I'm sorry, you cannot approve/reject this reservation.");
            // }
        } else {
            throw new ServiceException("Something went wrong. Please try again.");
        }
    }

    @Transactional
    @Override
    public void cancel(Long reservationId) {
        Reservation existingReservation = reservationRepository.findById(reservationId).orElseThrow(null);
        String username = SecurityUtil.getSessionUser();
        EmployeeAccount employeeAccount = employeeAccountRepository.findByUsername(username).get();
        Employee user = employeeAccount.getEmployee();
        if(existingReservation != null && existingReservation.getBooker().getId()== user.getId()  && (existingReservation.getStatus() == Reservation.Status.UNREVIEWED || existingReservation.getStatus()== Reservation.Status.APPROVED )  ) {
            existingReservation.setStatus(Reservation.Status.CANCELLED);
            reservationRepository.save(existingReservation);
        } else {
            throw new ServiceException("Something went wrong. Please try again");
        }
    }

    @Transactional
    @Override
    public void updateDetails(ReservationBean reservationBean) {
        Reservation existingReservation = reservationRepository.findById(reservationBean.getId()).orElseThrow(null);
        String username = SecurityUtil.getSessionUser();
        EmployeeAccount employeeAccount = employeeAccountRepository.findByUsername(username).get();
        Employee user = employeeAccount.getEmployee();

        if (existingReservation != null) {
            //if (reservationBean.getBooker().getId().equals(existingReservation.getBooker().getId()) && existingReservation.getStatus() == Reservation.Status.UNREVIEWED) {
            if (reservationBean.getBooker().getId().equals(user.getId()) && reservationBean.getBooker().getId().equals(existingReservation.getBooker().getId()) && existingReservation.getStatus() == Reservation.Status.UNREVIEWED) {

                boolean doesNotCollide = true;
                List<Reservation> existingReservations = reservationRepository.findAll().stream()
                        .filter(reservation -> reservation.getStatus().equals(Reservation.Status.APPROVED) ||
                                reservation.getStatus().equals(Reservation.Status.UNREVIEWED))
                        .collect(Collectors.toList());

                for (Reservation reservation : existingReservations) {
                    if (reservationBean.getEndTime().after(reservation.getStartTime()) &&
                            reservationBean.getStartTime().before(reservation.getEndTime())) {
                        doesNotCollide = false;
                        break;
                    }
                }

                if (doesNotCollide) {

                    existingReservation.setEventDate(reservationBean.getEventDate());
                    existingReservation.setStartTime(reservationBean.getStartTime());
                    existingReservation.setEndTime(reservationBean.getEndTime());
                    existingReservation.setTheatre(reservationBean.getTheatre());
                    existingReservation.setModifiedDate(new Date());
                    if(reservationBean.getTheatre().getCapacity() >= reservationBean.getAttendees()){
                        existingReservation.setAttendees(reservationBean.getAttendees());
                    }else{
                        throw new ServiceException("Reservation's attendees exceeded the theatre's capacity.");
                    }

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(reservationBean.getEventDate());
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                    boolean isWeekend = dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;

                    if (!isWeekend) {
                        existingReservation.setStatus(Reservation.Status.APPROVED);
                    }

                    try {
                        reservationRepository.save(existingReservation);
                    } catch (DataAccessException e) {
                        throw new ServiceException("Something went wrong. Please try again.");
                    }
                } else {
                    throw new ServiceException("Conflict with existing reservation/s.");
                }

            } else {
                throw new ServiceException("I'm sorry, you are not authorized to edit the reservation.");
            }
        } else {
            throw new ServiceException("Something went wrong. We can't seem to find the reservation that you want to modify.");
        }
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
