package com.adowsky.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
class MailService {
    private MailSender mailSender;

    void sendInvitationMail(String email, String inviter, String invitationHash) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(String.format("You have been invited to %s's library", invitationHash));
        mailMessage.setFrom("no-reply@homelibrary.localhost");
        mailMessage.setText(String.format("Hello!\n" +
                "You have been invited to %s's library.\n" +
                "You have to register first to have access to this library.\n" +
                "Click http://localhost:3000/#/register?invitation=%s to register", inviter, invitationHash));
        mailSender.send(mailMessage);
        log.info("Sent email to {}", email);
    }

    void sendRegistrationMail(String email, String confirmationCode) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Finish registration");
        mailMessage.setFrom("no-reply@homelibrary.localhost");
        mailMessage.setText(String.format("Hello!\n" +
                "Account registration finished successfully!\n" +
                "Next step is to confirm the registration. To confirm your account please click following link: " +
                "http://localhost:3000/#/confirm?code=%s", confirmationCode));
        mailSender.send(mailMessage);
        log.info("Sent email to {}", email);
    }

}
