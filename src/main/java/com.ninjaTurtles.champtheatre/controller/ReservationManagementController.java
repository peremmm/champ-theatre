package com.ninjaTurtles.champtheatre.controller;


import com.ninjaTurtles.champtheatre.bean.ReservationBean;
import com.ninjaTurtles.champtheatre.bean.TheatreBean;
import com.ninjaTurtles.champtheatre.exception.ServiceException;
import com.ninjaTurtles.champtheatre.models.Reservation;
import com.ninjaTurtles.champtheatre.models.Theatre;
import com.ninjaTurtles.champtheatre.service.EmployeeManagementService;
import com.ninjaTurtles.champtheatre.service.ReservationManagementService;
import com.ninjaTurtles.champtheatre.service.TheatreManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ReservationManagementController {

    private final ReservationManagementService reservationManagementService;
    private final EmployeeManagementService employeeManagementService;
    private TheatreManagementService theatreManagementService;


    @Autowired
    public ReservationManagementController(ReservationManagementService reservationManagementService, EmployeeManagementService employeeManagementService, TheatreManagementService theatreManagementService) {
        this.reservationManagementService = reservationManagementService;
        this.employeeManagementService = employeeManagementService;
        this.theatreManagementService = theatreManagementService;
    }

    @GetMapping("/requests")
    public String listAllReservations(Model model, RedirectAttributes redirectAttributes) {

        List<ReservationBean> reservations = reservationManagementService.findAll();

        model.addAttribute("reservations", reservations);

        if (redirectAttributes.containsAttribute("message")) {
            model.addAttribute("message", redirectAttributes.getAttribute("message"));
        }
        if (redirectAttributes.containsAttribute("error")) {
            model.addAttribute("error", redirectAttributes.getAttribute("error"));
        }
        return "requests-list";
    }


    /****** WAITING FOR USER SESSION *******/
    @GetMapping("/reservations")
    public String listUserReservations(Model model, RedirectAttributes redirectAttributes) {
        // Need user
        //List<ReservationBean> reservations = reservationManagementService.findByBooker(User Session ID);
        List<ReservationBean> reservations = reservationManagementService.findAll();
        model.addAttribute("reservations", reservations);

        if (redirectAttributes.containsAttribute("message")) {
            model.addAttribute("message", redirectAttributes.getAttribute("message"));
        }
        if (redirectAttributes.containsAttribute("error")) {
            model.addAttribute("error", redirectAttributes.getAttribute("error"));
        }
        return "reservation-list";
    }

    @GetMapping("/reservations/new")
    public String reservationForm(Model model) {

        List<TheatreBean> theatreBeans = theatreManagementService.getAllTheatre();
        List<ReservationBean> reservations = reservationManagementService.findAll().stream()
                .filter(reservation -> reservation.getStatus().equals(Reservation.Status.APPROVED) ||
                        reservation.getStatus().equals(Reservation.Status.UNREVIEWED))
                .collect(Collectors.toList());

        model.addAttribute("theaters", theatreBeans);
        model.addAttribute("reservations", reservations);
        model.addAttribute("newReservation", new Reservation());
        return "reservation-create";
    }

    @PostMapping("/reservations/new")
    public String createReservation(@ModelAttribute("reservation") ReservationBean reservationBean,
                                    @RequestParam("date") String eventDate,
                                    @RequestParam("start") String start,
                                    @RequestParam("end") String end,
                                    @RequestParam("theatreId") String theatreId,
                                    RedirectAttributes redirectAttributes) {

        LocalDate localDate = LocalDate.parse(eventDate);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        reservationBean.setEventDate(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(start));
        reservationBean.setStartTime(calendar.getTime());

        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(end));
        reservationBean.setEndTime(calendar.getTime());

        TheatreBean theatreBean = theatreManagementService.findTheatreById(Long.parseLong(theatreId));
        reservationBean.setTheatre(mapToTheatre(theatreBean));

        try {
            reservationManagementService.save(reservationBean);
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/reservations";
        }

        String message = "A new reservation request has been created.";
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/reservations";
    }


    @GetMapping("/reservations/{reservationId}/edit")
    public String editReservationForm(@PathVariable("reservationId") long reservationId, Model model) {
        ReservationBean reservationBean = reservationManagementService.findById(reservationId);
        List<TheatreBean> theatreBeans = theatreManagementService.getAllTheatre();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(reservationBean.getStartTime());
        int start = calendar.get(Calendar.HOUR_OF_DAY);

        calendar.setTime(reservationBean.getEndTime());
        int end = calendar.get(Calendar.HOUR_OF_DAY);


        List<ReservationBean> existingReservations = reservationManagementService.findAll().stream()
                .filter(reservation -> reservation.getStatus().equals(Reservation.Status.APPROVED) ||
                        reservation.getStatus().equals(Reservation.Status.UNREVIEWED))
                .collect(Collectors.toList());

        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("theaters", theatreBeans);
        model.addAttribute("reservations", existingReservations);
        model.addAttribute("reservation", reservationBean);
        model.addAttribute("defaultTheatreId", reservationBean.getTheatre().getId());
        return "reservation-edit";
    }

    @PostMapping("/reservations/{reservationId}/edit")
    public String updateReservation(@PathVariable("reservationId") Long reservationId,
                                    @RequestParam("date") String eventDate,
                                    @RequestParam("start") String start,
                                    @RequestParam("end") String end,
                                    @RequestParam("theatreId") String theatreId,
                                    @RequestParam("attendees") String attendees,
                                    RedirectAttributes redirectAttributes) {
        ReservationBean existingReservation = reservationManagementService.findById(reservationId);
        LocalDate localDate = LocalDate.parse(eventDate);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        existingReservation.setEventDate(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(start));
        existingReservation.setStartTime(calendar.getTime());

        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(end));
        existingReservation.setEndTime(calendar.getTime());

        if (existingReservation.getTheatre().getId() != Long.parseLong(theatreId)) {
            TheatreBean theatreBean = theatreManagementService.findTheatreById(Long.parseLong(theatreId));
            existingReservation.setTheatre(mapToTheatre(theatreBean));
        }
        int expectedAttendees = Integer.parseInt(attendees);
        if (existingReservation.getAttendees() != expectedAttendees) {
            existingReservation.setAttendees(expectedAttendees);
        }
        try {
            reservationManagementService.updateDetails(existingReservation);
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/reservations/" + existingReservation.getId() + "/edit";
        }

        String message = "The reservation request with id " + existingReservation.getId() + " has been updated.";
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/reservations";
    }


    @GetMapping("/reservations/{reservationId}/cancel")
    public String cancelReservation(@PathVariable("reservationId") Long reservationId,
                                    RedirectAttributes redirectAttributes) {

        try {
            reservationManagementService.cancel(reservationId);
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/reservations";
        }

        redirectAttributes.addFlashAttribute("message",
                "Reservation " + reservationId + " has been canceled");
        return "redirect:/reservations";
    }

    @GetMapping("/reservations/{reservationId}/approve")
    public String approveReservation(@PathVariable("reservationId") Long reservationId,
                                     RedirectAttributes redirectAttributes) {
        try {
            reservationManagementService.updateStatus(reservationId, Reservation.Status.APPROVED);
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/requests";
        }

        redirectAttributes.addFlashAttribute("message",
                "Reservation " + reservationId + " has been approved");
        return "redirect:/requests";
    }

    @GetMapping("/reservations/{reservationId}/reject")
    public String rejectReservation(@PathVariable("reservationId") Long reservationId,
                                    RedirectAttributes redirectAttributes) {
        try {
            reservationManagementService.updateStatus(reservationId, Reservation.Status.REJECTED);
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/requests";
        }

        redirectAttributes.addFlashAttribute("message",
                "Reservation " + reservationId + " has been approved");
        return "redirect:/requests";
    }

    @GetMapping("/reservations/{reservationId}/assign")
    public String assignUserToReviewReservation(@PathVariable("reservationId") Long reservationId,
                                                RedirectAttributes redirectAttributes) {
        try {
            reservationManagementService.assign(reservationId);
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/requests";
        }

        redirectAttributes.addFlashAttribute("message",
                "Reservation " + reservationId + " is now waiting for your approval");
        return "redirect:/requests";
    }

    private Theatre mapToTheatre(TheatreBean theatre) {
        return Theatre.builder()
                .id(theatre.getId())
                .name(theatre.getName())
                .status(theatre.getStatus())
                .capacity(theatre.getCapacity())
                .reservations(theatre.getReservations())
                .createdDate(theatre.getCreatedDate())
                .modifiedDate(theatre.getModifiedDate())
                .build();
    }

}
