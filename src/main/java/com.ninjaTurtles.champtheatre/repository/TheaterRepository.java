//package com.ninjaTurtles.champtheatre.repository;
//
//import com.ninjaTurtles.champtheatre.models.Reservation;
//import com.ninjaTurtles.champtheatre.models.Theatre;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface TheaterRepository extends JpaRepository<Theatre, Long> {
//    Optional<Theatre> findByStatus(Theatre.Status status);
//    Optional<Theatre> findByName(String name);
//    Optional<Theatre> findByReservation(List<Reservation> reservations);
//}
