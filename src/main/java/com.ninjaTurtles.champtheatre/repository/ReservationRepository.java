package com.ninjaTurtles.champtheatre.repository;

import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.Reservation;
import com.ninjaTurtles.champtheatre.models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByStatus(Reservation.Status status);
    Optional<Reservation> findByBooker(Employee booker);
    Optional<Reservation> findByReviewer(Employee reviewer);
    Optional<Reservation> findByEventDate(Date eventDate);
    Optional<Reservation> findByTheatre(Theatre theatre);
}
