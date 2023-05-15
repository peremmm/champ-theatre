package com.ninjaTurtles.champtheatre.controller;

import com.ninjaTurtles.champtheatre.bean.EmailMessageBean;
import com.ninjaTurtles.champtheatre.service.EmailSenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailSenderService emailSenderService;

    public EmailController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/send-email")
    public ResponseEntity sendEmail(@RequestBody EmailMessageBean emailMessageBean) {
        this.emailSenderService.sendEmail(emailMessageBean.getTo(), emailMessageBean.getSubject(), emailMessageBean.getMessage());
        return ResponseEntity.ok("Success");
    }
}
