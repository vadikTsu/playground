package org.playground.services;

import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService  {

    @Autowired
    @Qualifier("mailSenderS")
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String text) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("vadym_tsudenko@knu.ua");
            message.setSubject(subject);
            message.setText(text);
            javaMailSender.send(message);
    }









}
