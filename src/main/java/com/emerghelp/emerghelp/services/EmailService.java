package com.emerghelp.emerghelp.services;

import com.emerghelp.emerghelp.dtos.responses.SendMailResponse;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;


public interface EmailService {
    SendMailResponse sendHtmlEmail(String name, String to);
    SendMailResponse sendHtmlEmailToMedic(String name, String to);

}