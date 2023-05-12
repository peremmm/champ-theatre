package com.ninjaTurtles.champtheatre.controller;

import com.ninjaTurtles.champtheatre.bean.TheatreBean;
import com.ninjaTurtles.champtheatre.models.Theatre;
import com.ninjaTurtles.champtheatre.service.TheatreManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class TheatreManagementController {
    private final TheatreManagementService theatreManagementService;

    @Autowired
    public TheatreManagementController(TheatreManagementService theatreManagementService) {
        this.theatreManagementService = theatreManagementService;
    }

    @GetMapping("/theatres")
    public String GetAllTheatres(Model model){
        List<TheatreBean> theatres = theatreManagementService.getAllTheatre();
        model.addAttribute("theatres", theatres);
        return "theatre-list";
    }

    @GetMapping("/theatres/{theatreId}")
    public String getTheatreById(@PathVariable("theatreId") long theatreId, Model model){
        TheatreBean theatreBean = theatreManagementService.findTheatreById(theatreId);
        model.addAttribute("theatre",theatreBean);
        return "theatre-details";
    }

    @GetMapping("/theatres/{theatreId}/update")
    public String updateTheatreDetails(@PathVariable("theatreId") Long theatreId, Model model) {
        TheatreBean theatre = theatreManagementService.findTheatreById(theatreId);
        model.addAttribute("theatre", theatre);
        model.addAttribute("statuses", Theatre.Status.values());
        return "theatre-edit-details";
    }

    @PostMapping("/theatres/{theatreId}/update")
    public String saveTheatreDetails(@PathVariable("theatreId") Long theatreId,
                                     @ModelAttribute("theatre") Theatre theatre,
                                     BindingResult result,
                                     Model model) {
        if (result.hasErrors()) {
            model.addAttribute("theatre", theatre);
            model.addAttribute("statuses", Theatre.Status.values());
            return "theatre-edit-details";
        }
        theatre.setId(theatreId);
        theatreManagementService.updateTheatreDetails(theatre);
        return "redirect:/theatres/" + theatreId;
    }


    @GetMapping("/theatres/{theatreId}/change-status")
    public String changeTheatreStatusForm(@PathVariable("theatreId") Long theatreId, Model model) {
        TheatreBean theatre = theatreManagementService.findTheatreById(theatreId);
        model.addAttribute("theatre", theatre);
        model.addAttribute("statuses", Theatre.Status.values());
        return "theatre-edit-status";
    }

    @PostMapping("/theatres/{theatreId}/change-status")
    public String saveTheatreStatus(@PathVariable Long theatreId, @ModelAttribute("theatre") Theatre theatre,
                                      BindingResult result, Model model) {
        if (result.hasErrors()){
            model.addAttribute("theatre", theatre);
            model.addAttribute("statuses", Theatre.Status.values());
            return "theatre-edit-status";
        }
        theatre.setId(theatreId);
        theatreManagementService.changeTheatreStatus(theatre);
        return "redirect:/theatres/" + theatreId;
    }

}
