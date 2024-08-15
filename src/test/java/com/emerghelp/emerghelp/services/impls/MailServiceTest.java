package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.dtos.responses.SendMailResponse;
import com.emerghelp.emerghelp.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MailServiceTest {
    @Autowired
    private EmailService emailService;
    @Test
    public void testSendSimpleMailMessage() {
        String name = "ddon";
        String recipientEmail = "ericsonericdon66@gmail.com";
        SendMailResponse response = emailService.sendHtmlEmail(name, recipientEmail);
        assertTrue(response.getMessage().contains("sent successfully"));

    }
    @Test
    public void testSendHtmlEmail() {
        String name = "Izuchukwu";
        String recipientEmail = "izuchukwuijeudo@gmail.com";
        SendMailResponse response = emailService.sendHtmlEmail(name, recipientEmail);
        assertTrue(response.getMessage().contains("sent successfully"));
        assertTrue(true, "Email sent successfully");
    }
}
