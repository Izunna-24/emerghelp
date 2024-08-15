package com.emerghelp.emerghelp.services.impls;

import com.emerghelp.emerghelp.config.MailConfig;
import com.emerghelp.emerghelp.dtos.responses.SendMailResponse;
import com.emerghelp.emerghelp.exceptions.EmailAlreadyExistException;
import com.emerghelp.emerghelp.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmergHelpMailServiceImpl implements EmailService {
    public static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
    public static final String UTF_8_ENCODING = "UTF-8";
    public static final String EMAIL_TEMPLATE = "emailtemplate";
    public static final String EMAIL_TEMPLATE_TO_MEDIC = "emailtemplateformedic";
    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    @Autowired
    private MailConfig mailConfig;

    @Override
    public SendMailResponse sendHtmlEmail(String name, String to) {
        try {
            Context context = new Context();
            context.setVariables(Map.of("name", name));
            context.setVariables(Map.of("host", mailConfig.getHost()));
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
            throw new EmailAlreadyExistException("Email already exist");
        }
        SendMailResponse sendMailResponse = new SendMailResponse();
        sendMailResponse.setMessage("Mail sent successfully");
        return sendMailResponse;
    }

    @Override
    public SendMailResponse sendHtmlEmailToMedic(String name, String to) {
        try {
            Context context = new Context();
            context.setVariables(Map.of("name", name));
            context.setVariables(Map.of("host", mailConfig.getHost()));
            String text = templateEngine.process(EMAIL_TEMPLATE_TO_MEDIC, context);
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
            throw new EmailAlreadyExistException("Email already exist");
        }
        SendMailResponse sendMailResponse = new SendMailResponse();
        sendMailResponse.setMessage("Mail sent successfully");
        return sendMailResponse;
    }


    private MimeMessage getMimeMessage() {
        return emailSender.createMimeMessage();
    }
}