package com.stalary.pf.outside.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * MailService
 *
 * @author lirongqian
 * @since 2018/04/27
 */
@Service
@Slf4j
@RefreshScope
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String message) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(from);
            mailMessage.setTo(to);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);

            mailSender.send(mailMessage);
            log.info("Mail sent to {}", to);

        } catch (Exception e) {
            log.error("Failed to send mail to {}", to, e);
        }
    }
}
