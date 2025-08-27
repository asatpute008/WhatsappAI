package Project;

import java.util.Properties;
import java.util.Random;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class SendPasscodeEmail {
	public static void main(String[] args) {
		SendPasscodeEmail sendPasscodeEmail = new SendPasscodeEmail();
		sendPasscodeEmail.passcode();
	}

	public String passcode() {
        String to = "asatpute008@gmail.com"; // Receiver email
        String from = "asatpute008@gmail.com"; // Sender email
        String host = "smtp.gmail.com"; // Gmail SMTP
        String password = "hwck tsjd bpov xrst"; // Use App Password

        // Generate 6-digit passcode
        String passcode = String.valueOf(100000 + new Random().nextInt(900000));

        // Setup mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create session
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Your Passcode");
            message.setText("Your passcode is: " + passcode);

            // Send message
            Transport.send(message);

            return passcode;

        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
