package com.epam.committee.email;

import com.epam.committee.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SendEmail {
    private final static Logger logger = LogManager.getLogger();
    private static final String PATH_CONFIG = "mail.properties";
    private static final String USER = "mail.user.name";
    private static final String PASSWORD = "mail.user.password";
    private static final String HOST_SMTP = "mail.smtp.host";
    private static final String SMTP_SSL = "mail.smtp.ssl.enable";
    private static final String SMTP_PORT = "mail.smtp.port";
    private static final String SMTP_AUTH = "mail.smtp.auth";

    public void send(String sendTo, String subject, String messageToSend) throws ServiceException {
        Properties properties = loadProperties(PATH_CONFIG);
        properties.getProperty(HOST_SMTP);
        properties.getProperty(SMTP_SSL);
        properties.getProperty(SMTP_PORT);
        properties.getProperty(SMTP_AUTH);
        final String user = properties.getProperty(USER);
        final String password = properties.getProperty(PASSWORD);
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            InternetAddress[] address = {new InternetAddress(sendTo)};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(subject);
            message.setText(messageToSend);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.error("Error sending email!", e);
            throw new ServiceException(e);
        }
    }

    private Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.log(Level.FATAL, "Reading file error", e);
            throw new RuntimeException(e);
        }
        return properties;
    }
}