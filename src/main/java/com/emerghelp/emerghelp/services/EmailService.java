package com.emerghelp.emerghelp.services;

import com.emerghelp.emerghelp.dtos.responses.SendMailResponse;
import org.springframework.stereotype.Service;


public interface EmailService {
    SendMailResponse sendHtmlEmail(String name, String to, String token);

}