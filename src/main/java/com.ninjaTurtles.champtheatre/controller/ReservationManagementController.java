package com.ninjaTurtles.champtheatre.controller;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.bean.ReservationBean;
import com.ninjaTurtles.champtheatre.bean.TheatreBean;
import com.ninjaTurtles.champtheatre.exception.ServiceException;
import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.Reservation;
import com.ninjaTurtles.champtheatre.service.EmployeeManagementService;
import com.ninjaTurtles.champtheatre.service.ReservationManagementService;
import com.ninjaTurtles.champtheatre.service.TheatreManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
        System.out.println("--------------------------------------ENTERING REQUESTS");
        List<ReservationBean> reservations = reservationManagementService.findAll();
        //System.out.println("--------------------------------------REQUESTS"+reservations);
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
        //List<ReservationBean> reservations = reservationManagementService.findByBooker(new Employee());
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
    public String reservationForm(Model model){

        List<TheatreBean> theatreBeans = theatreManagementService.getAllTheatre();
        String startTime = null;
        String endTime = null;

        model.addAttribute("theaters", theatreBeans);
        //model.addAttribute("starts", startTime);
        //model.addAttribute("ends", endTime);
        model.addAttribute("reservation", new Reservation());
        return "reservation-create";
    }

    @PostMapping("/reservations/new")
    public String createReservation(@ModelAttribute("reservationBean") ReservationBean reservationbean, BindingResult result, RedirectAttributes redirectAttributes){
        if( result.hasErrors()) {
            return "reservations-create";
        }

        try {
            reservationManagementService.save(reservationbean);
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("error", "Something went wrong. Pleas try again. \n"+ e.getMessage());
            return "redirect:/reservations/new"; // Redirect to the new employee form
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
