package com.ninjaTurtles.champtheatre.service.impl;

import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import com.ninjaTurtles.champtheatre.models.Reservation;
import com.ninjaTurtles.champtheatre.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender mailSender;

    public EmailSenderServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("champ.theatre.mail@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        this.mailSender.send(simpleMailMessage);
    }

    @Override
    public void sendAutoGeneratedPassword(Employee employee, EmployeeAccount account) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(employee.getEmail());
        messageHelper.setSubject("Auto-generated Password");
        messageHelper.setText("Your auto-generated password is: " + account.getPassword());

        mailSender.send(mimeMessage);
    }


    @Override
    public void sendReserveStatusUpdate(Reservation reservation, Employee employee) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(employee.getEmail());
        messageHelper.setSubject("Reservation Status Update");
        messageHelper.setText("Your reservation status is now: " + reservation.getStatus().toString());

        mailSender.send(mimeMessage);
    }


    @Override
    public void sendPasswordChange(Employee employee) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(employee.getEmail());
        messageHelper.setSubject("Password Change Notification");
        messageHelper.setText("Your password has been changed successfully.");

        mailSender.send(mimeMessage);
    }


    public void sendReservationConfirmation(Reservation reservation, Employee employee) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(employee.getEmail());
        messageHelper.setSubject("Reservation Confirmation");
        messageHelper.setText("Dear " + employee.getFirstName() + ",\n\n" +
                "Your reservation has been confirmed successfully.\n\n" +
                "Reservation Details:\n" +
                "Date: " + reservation.getEventDate() + "\n" +
                "Time: " + reservation.getStartTime() + " - " + reservation.getEndTime() + "\n");

        mailSender.send(mimeMessage);
    }

}
