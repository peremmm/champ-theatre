package com.ninjaTurtles.champtheatre.controller;

import com.ninjaTurtles.champtheatre.bean.EmailMessageBean;
import com.ninjaTurtles.champtheatre.service.EmailSenderService;
import com.ninjaTurtles.champtheatre.service.EmployeeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

//    @Autowired
    private final EmployeeManagementService employeeManagementService;
    private final EmailSenderService emailSenderService;

    @Autowired
    public EmailController(EmployeeManagementService employeeManagementService, EmailSenderService emailSenderService) {
        this.employeeManagementService = employeeManagementService;
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/send-email")
    public ResponseEntity sendEmail(@RequestBody EmailMessageBean emailMessageBean) {
        Long employeeId = emailMessageBean.getEmployeeId();
        String employeeEmail = employeeManagementService.getEmployeeEmailById(employeeId);
        this.emailSenderService.sendEmail(employeeEmail, emailMessageBean.getSubject(), emailMessageBean.getMessage());
        return ResponseEntity.ok("Success");
    }
}
