package com.ninjaTurtles.champtheatre.controller;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.bean.ReservationBean;
import com.ninjaTurtles.champtheatre.exception.ServiceException;
import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import com.ninjaTurtles.champtheatre.models.Reservation;
import com.ninjaTurtles.champtheatre.service.EmployeeManagementService;
import com.ninjaTurtles.champtheatre.service.ReservationManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ReservationManagementController {

    private final ReservationManagementService reservationManagementService;


    @Autowired
    public ReservationManagementController(ReservationManagementService reservationManagementService) {
        this.reservationManagementService = reservationManagementService;
    }

    @GetMapping("/allReservations")
    public String listAllReservations(Model model, RedirectAttributes redirectAttributes) {

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


    /****** WAITING FOR USER SESSION ********/
    @GetMapping("/reservations")
    public String listReservations(Model model, RedirectAttributes redirectAttributes) {

        List<ReservationBean> reservations = reservationManagementService.findByBooker(new Employee());
        model.addAttribute("reservations", reservations);

        if (redirectAttributes.containsAttribute("message")) {
            model.addAttribute("message", redirectAttributes.getAttribute("message"));
        }
        if (redirectAttributes.containsAttribute("error")) {
            model.addAttribute("error", redirectAttributes.getAttribute("error"));
        }
        return "reservation-list";
    }

    @PostMapping("/reservations/new")
    public String createReservation(@ModelAttribute("reservation") ReservationBean reservationbean, RedirectAttributes redirectAttributes){
        try {
            reservationManagementService.save(reservationbean);
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("error", "Something went wrong. Pleas try again. \n"+ e.getMessage());
            return "redirect:/reservations/edit"; // Redirect to the new employee form
        }

        String message = "A new reservation request has been created. Please wait for approval.";
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/reservations";
    }

    @PostMapping("/reservations/assign")
    public String assignReviewer(@ModelAttribute("reservation") ReservationBean reservationbean, RedirectAttributes redirectAttributes){
        try {
            reservationManagementService.updateStatus(reservationbean);
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/reservations/edit"; // Redirect to the new employee form
        }

        String message = "The reservation request with id "+reservationbean.getId()+" is now being processed.";
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/reservations";
    }

    @PostMapping("/reservations/edit")
    public String updateReservation(@ModelAttribute("reservation") ReservationBean reservationbean, RedirectAttributes redirectAttributes){
        try {
            reservationManagementService.updateDetails(reservationbean);
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/reservations/edit"; // Redirect to the new employee form
        }

        String message = "The reservation request with id "+reservationbean.getId()+" has been updated.";
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/reservations";
    }

}
