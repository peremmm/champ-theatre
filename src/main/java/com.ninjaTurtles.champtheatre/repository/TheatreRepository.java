package com.ninjaTurtles.champtheatre.repository;

import com.ninjaTurtles.champtheatre.models.Reservation;
import com.ninjaTurtles.champtheatre.models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    Optional<Theatre> findByStatus(Theatre.Status status);
    Optional<Theatre> findByName(String name);
    List<Theatre> findAllByReservationsContaining(Reservation reservation);
}
