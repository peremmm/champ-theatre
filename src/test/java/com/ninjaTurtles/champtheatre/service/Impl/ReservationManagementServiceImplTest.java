package com.ninjaTurtles.champtheatre.service.impl;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.bean.ReservationBean;
import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.Participant;
import com.ninjaTurtles.champtheatre.models.Reservation;
import com.ninjaTurtles.champtheatre.models.Theatre;
import com.ninjaTurtles.champtheatre.repository.EmployeeRepository;
import com.ninjaTurtles.champtheatre.repository.ReservationRepository;
import com.ninjaTurtles.champtheatre.repository.TheatreRepository;
import com.ninjaTurtles.champtheatre.service.impl.ReservationManagementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;

import java.time.*;
import java.util.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ReservationManagementServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private TheatreRepository theatreRepository;

    @InjectMocks
    private ReservationManagementServiceImpl reservationManagementService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindReservationsById() {
        // Create a test reservation

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setEvent_description("Test Event");

        LocalDateTime eventDate = LocalDateTime.of(2023, 5, 11, 10, 0);
        Date eventDateAsDate = Date.from(eventDate.atZone(ZoneId.systemDefault()).toInstant());
        reservation.setEventDate(eventDateAsDate);

        // --Set startTime property
        LocalTime startTime = LocalTime.of(10, 0);
        reservation.setStartTime(Date.from(startTime.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant()));

        // --Set endTime property
        LocalTime endTime = LocalTime.of(12, 0);
        reservation.setEndTime(Date.from(endTime.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant()));

        // --Set theatre property
        Theatre theatre = new Theatre();
        theatre.setName("Theatre 1");
        reservation.setTheatre(theatre);

        // --Set booker property
        Employee booker = new Employee();
        booker.setFirstName("John");
        booker.setLastName("Doe");
        reservation.setBooker(booker);

        // --Set reviewer property
        Employee reviewer = new Employee();
        reviewer.setFirstName("Jane");
        reviewer.setLastName("Smith");
        reservation.setReviewer(reviewer);

        // --Set participants property
        Participant participant = new Participant();

        List<Participant> participants = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            participants.add(participant);
        }
        reservation.setParticipants(participants);

        // --Set status property
        reservation.setStatus(Reservation.Status.APPROVED);
        reservation.setStatus(Reservation.Status.CANCELLED);
        reservation.setStatus(Reservation.Status.REJECTED);
        reservation.setStatus(Reservation.Status.PENDING);

        // Mock the repository response
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        // Call the service method
        ReservationBean reservationBean = reservationManagementService.findById(1L);

        // Verify the repository method was called
        verify(reservationRepository, times(1)).findById(1L);

        // Assert the result
        assertEquals(reservation.getId(), reservationBean.getId());
        assertEquals(reservation.getEvent_description(), reservationBean.getEvent_description());
        assertEquals(reservation.getEventDate(), reservationBean.getEventDate());
        assertEquals(reservation.getStartTime(), reservationBean.getStartTime());
        assertEquals(reservation.getEndTime(), reservationBean.getEndTime());
        assertEquals(reservation.getTheatre(), reservationBean.getTheatre());
        assertEquals(reservation.getBooker(), reservationBean.getBooker());
        assertEquals(reservation.getReviewer(), reservationBean.getReviewer());
        assertEquals(reservation.getParticipants(), reservationBean.getParticipants());
        assertEquals(reservation.getStatus(), reservationBean.getStatus());
        assertEquals(reservation.getModifiedDate(), reservationBean.getModifiedDate());
        assertEquals(reservation.getCreatedDate(), reservationBean.getCreatedDate());
    }


    @Test
    public void shouldFindReservationsByBooker(){

        // Create a test booker
        Employee booker = new Employee();
        booker.setId(1L);
        booker.setFirstName("John");
        booker.setLastName("Doe");
        booker.setEmail("john@example.com");

        // Create a test theatre
        Theatre theatre = new Theatre();
        theatre.setId(1L);
        theatre.setName("Theatre 1");

        // Create a test participant
        Participant participant = new Participant();
        participant.setId(1L);
        participant.setEmployee(booker);

        // Create a test reservation
        Reservation reservation1 = new Reservation();
        reservation1.setId(1L);
        reservation1.setEvent_type(Reservation.Type.BUSINESS);
        reservation1.setEvent_description("Business Event 1");
        Date date = new Date();
        date.setYear(123);
        date.setMonth(4); // May is 0-based, so 4 represents May
        date.setDate(19);
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);

        reservation1.setEventDate(date);
        date.setHours(8);
        reservation1.setStartTime(date);
        date.setHours(11);
        reservation1.setEndTime(date);
        reservation1.setStatus(Reservation.Status.APPROVED);
        reservation1.setTheatre(theatre);
        reservation1.setBooker(booker);
        reservation1.setReviewer(null); // Set reviewer to null for this test
        reservation1.setParticipants(new ArrayList<>());
        reservation1.getParticipants().add(participant);
        reservation1.setModifiedDate(new Date());
        reservation1.setCreatedDate(new Date());



        // Mock the repository response
        // Mock the repository response
        Optional<Reservation> optionalReservation = Optional.of(reservation1);
        List<Reservation> reservations = optionalReservation.map(Collections::singletonList).orElse(Collections.emptyList());

        when(reservationRepository.findByBooker(booker)).thenReturn(optionalReservation);

        // Call the service method
        List<ReservationBean> reservationBeans = reservationManagementService.findByBooker(booker);

        // Verify the repository method was called
        verify(reservationRepository, times(1)).findByBooker(booker);

        // Assert the result
        assertEquals(reservations.size(), reservationBeans.size());
        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            ReservationBean reservationBean = reservationBeans.get(i);

            assertEquals(reservation.getId(), reservationBean.getId());
            assertEquals(reservation.getEvent_type(), reservationBean.getEvent_type());
            assertEquals(reservation.getEvent_description(), reservationBean.getEvent_description());
            assertEquals(reservation.getEventDate(), reservationBean.getEventDate());
            assertEquals(reservation.getStartTime(), reservationBean.getStartTime());
            assertEquals(reservation.getEndTime(), reservationBean.getEndTime());
            assertEquals(reservation.getStatus(), reservationBean.getStatus());
            assertEquals(reservation.getTheatre(), reservationBean.getTheatre());
            assertEquals(reservation.getBooker(), reservationBean.getBooker());
            assertEquals(reservation.getReviewer(), reservationBean.getReviewer());
            assertEquals(reservation.getParticipants(), reservationBean.getParticipants());
            assertEquals(reservation.getModifiedDate(), reservationBean.getModifiedDate());
            assertEquals(reservation.getCreatedDate(), reservationBean.getCreatedDate());
            }
        }

    @Test
    public void shouldReturnEmptyReservationRequests() {
        // Mock an empty list of employees
        when(reservationRepository.findAll()).thenReturn(new ArrayList<>());

        // Call the service method
        List<ReservationBean> reservationBeans = reservationManagementService.findAll();

        // Verify the repository method was called
        verify(reservationRepository, times(1)).findAll();

        // Assert the result
        assertEquals(0, reservationBeans.size());
    }


/*    @Test
    public void shouldCreateReservation() {
            // Create a test reservation bean
            ReservationBean reservationBean = new ReservationBean();
            reservationBean.setEvent_type(Reservation.Type.LEISURE);
            reservationBean.setEvent_description("Test Event");
            reservationBean.setEventDate(new Date());
            // Set other properties of the reservation bean...

            // Create a test booker
            Employee booker = new Employee();
            booker.setId(204L);
            // Set other properties of the booker...

            // Mock the employee repository response
            when(employeeRepository.getById(204L)).thenReturn(booker);

            // Call the service method
            reservationManagementService.save(reservationBean);

            // Verify the repository method was called
            verify(reservationRepository, times(1)).save(Mockito.any(Reservation.class));

    }

  */
}
