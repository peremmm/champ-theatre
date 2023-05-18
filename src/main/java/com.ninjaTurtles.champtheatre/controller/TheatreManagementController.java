package com.ninjaTurtles.champtheatre.controller;

import com.ninjaTurtles.champtheatre.bean.TheatreBean;
import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import com.ninjaTurtles.champtheatre.models.Theatre;
import com.ninjaTurtles.champtheatre.security.SecurityUtil;
import com.ninjaTurtles.champtheatre.service.EmployeeManagementService;
import com.ninjaTurtles.champtheatre.service.TheatreManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class TheatreManagementController {
    private final TheatreManagementService theatreManagementService;

    @Autowired
    public TheatreManagementController(TheatreManagementService theatreManagementService) {
        this.theatreManagementService = theatreManagementService;
    }

    @GetMapping("/theatres")
    public String listTheatres(Model model, RedirectAttributes redirectAttributes) {
//        Optional<EmployeeAccount> employeeAccount = new EmployeeAccount();
        List<TheatreBean> theatres = theatreManagementService.getAllTheatre();
//        String username = SecurityUtil.getSessionUser();
//        if (username != null){
//            employeeAccount = employeeManagementService.findByUsername(username);
//            String fullname = employeeAccount.
            model.addAttribute("theatres", theatres);
//        }


        if (redirectAttributes.containsAttribute("message")) {
            model.addAttribute("message", redirectAttributes.getAttribute("message"));
        }
        if (redirectAttributes.containsAttribute("error")) {
            model.addAttribute("error", redirectAttributes.getAttribute("error"));
        }
        return "theatre-list";
    }


    @GetMapping("/theatres/{theatreId}/edit")
    public String editTheatreDetailsForm(@PathVariable("theatreId") Long theatreId, Model model) {
        TheatreBean theatre = theatreManagementService.findTheatreById(theatreId);
        model.addAttribute("theatre", theatre);
        model.addAttribute("statuses", Theatre.Status.values());
        return "theatre-edit-details";
    }

    @PostMapping("/theatres/{theatreId}/edit")
    public String updateTheatreDetails(@PathVariable("theatreId") Long theatreId,
                                     @Valid @ModelAttribute("theatre") TheatreBean theatreBean,
                                     BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "theatre-edit-details";
        }
        theatreBean.setId(theatreId);
        theatreManagementService.updateTheatreDetails(theatreBean);
        redirectAttributes.addFlashAttribute("message",
                "Theatre " + theatreBean.getId() + " has been EDITED");
        return "redirect:/theatres";
    }
}
