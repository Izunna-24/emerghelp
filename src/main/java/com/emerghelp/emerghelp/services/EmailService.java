package com.emerghelp.emerghelp.services;

import com.emerghelp.emerghelp.dtos.responses.SendMailResponse;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    SendMailResponse sendSimpleMailMessage(String name, String to, String token);
    SendMailResponse sendHtmlEmail(String name, String to, String token);

}