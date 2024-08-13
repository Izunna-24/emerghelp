package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.dtos.responses.SendMailResponse;
import com.emerghelp.emerghelp.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MailServiceTest {

    private JavaMailSender emailSender;
    @Autowired
    private EmailService emailService;
    @Test
    public void testSendSimpleMailMessage() {
        String name = "ddon";
        String recipientEmail = "ericsonericdon66@gmail.com";
        String token = "some-secret-token";
        SendMailResponse response = emailService.sendHtmlEmail(name, recipientEmail, token);
        assertTrue(response.getMessage().contains("sent successfully"));

    }
    @Test
    public void testSendHtmlEmail() {
        String name = "Izuchukwu";
        String recipientEmail = "izuchukwuijeudo@gmail.com";
        String token = "some-secret-token";
        SendMailResponse response = emailService.sendHtmlEmail(name, recipientEmail, token);
        assertTrue(response.getMessage().contains("sent successfully"));
        assertTrue(true, "Email sent successfully");
    }
}
