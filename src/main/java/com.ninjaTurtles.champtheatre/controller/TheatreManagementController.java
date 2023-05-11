package com.ninjaTurtles.champtheatre.controller;

import com.ninjaTurtles.champtheatre.bean.TheatreBean;
import com.ninjaTurtles.champtheatre.models.Theatre;
import com.ninjaTurtles.champtheatre.service.TheatreManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TheatreManagementController {
    private final TheatreManagementService theatreManagementService;

    @Autowired
    public TheatreManagementController(TheatreManagementService theatreManagementService) {
        this.theatreManagementService = theatreManagementService;
    }

    @GetMapping("/theatres")
    public String listTheatres(Model model){
        List<TheatreBean> theatres = theatreManagementService.getAllTheatre();
        model.addAttribute("theatres", theatres);
        return "theatre-list";
    }

    @GetMapping("/theatres/{theatreId}/edit")
    public String editStatusForm(@PathVariable("theatreId")Long theatreId, Model model){
        TheatreBean theatre = theatreManagementService.findTheatreById(theatreId);
        model.addAttribute("theatre",theatre);
        return "theatre-edit";
    }

    @PostMapping("/theatres/{theatreId}/edit")
    public String editTheatreDetails(@PathVariable("theatreId")Long theatreId,
                                     @ModelAttribute("theatre") Theatre theatre,
                                     BindingResult result,
                                     Model model){
        if (result.hasErrors()){
            model.addAttribute("theatre", theatre);
            return "theatre-edit";
        }
        theatre.setId(theatreId);
        theatreManagementService.updateTheatreDetails(theatre);
        return "redirect:/theatres";
    }
}
