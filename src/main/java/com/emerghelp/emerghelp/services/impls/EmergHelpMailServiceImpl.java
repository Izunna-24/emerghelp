package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.config.MailConfig;
import com.emerghelp.emerghelp.dtos.responses.SendMailResponse;
import com.emerghelp.emerghelp.exceptions.EmailAlreadyExistException;
import com.emerghelp.emerghelp.services.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.Map;
import static com.emerghelp.emerghelp.security.utils.EmailUtils.getVerificationUrl;


@Service
@RequiredArgsConstructor
public class EmergHelpMailServiceImpl implements EmailService {
    public static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
    public static final String UTF_8_ENCODING = "UTF-8";
    public static final String EMAIL_TEMPLATE = "emailtemplate";
    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    @Autowired
    private MailConfig mailConfig;
    @Override
    public SendMailResponse sendHtmlEmail(String name, String to, String token) {
        try {
            Context context = new Context();
            context.setVariables(Map.of("name", name, "url", getVerificationUrl(mailConfig.getHost(), token)));
            String text = templateEngine.process(EMAIL_TEMPLATE, context);
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(mailConfig.getFromEmail());
            helper.setTo(to);
            helper.setText(text, true);
            emailSender.send(message);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new EmailAlreadyExistException(exception.getMessage());
        }
        SendMailResponse sendMailResponse = new SendMailResponse();
        sendMailResponse.setMessage("Mail sent successfully");
        return sendMailResponse;
    }

    private MimeMessage getMimeMessage() {
        return emailSender.createMimeMessage();
    }
}