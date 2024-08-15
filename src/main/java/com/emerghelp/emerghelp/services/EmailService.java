package com.emerghelp.emerghelp.services;

import com.emerghelp.emerghelp.dtos.responses.SendMailResponse;

public interface EmailService {
    SendMailResponse sendHtmlEmail(String name, String to);
    SendMailResponse sendHtmlEmailToMedic(String name, String to);

}