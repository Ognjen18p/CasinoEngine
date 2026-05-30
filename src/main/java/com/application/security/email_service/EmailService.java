package com.application.security.email_service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailService {
    private static EmailService instance;
    private static final String FROM = "casino.engine.email@gmail.com";
    private static final String PASSWORD = "uqrl ratn oaug fjir";
    private static final String HOST = "smtp.gmail.com";
    private static final String PORT = "587";
    private String errorMessage;

    public  String getErrorMessage() {
        return errorMessage;
    }

    public static EmailService getInstance() {
        if (instance == null) instance = new EmailService();
        return instance;
    }

    public void sendVerificationEmail(String recipientEmail, String token) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", PORT);

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PASSWORD);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Casino Engine - Email Verification");
            message.setText("Your verification code is: " + token);

            Transport.send(message);

        } catch (Exception exception) {
            errorMessage = "Sending verification email failed: " + exception.getMessage();
        }
    }

}
