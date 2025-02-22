package org.codingburgas.zsmihaleva20.exotic_destination_management_system.services;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Reservation;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.User;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Properties;

@Service
public class MailService {

    private final static String MAIL_SERVER_PASSWORD = "i,YFfe7yh=jC";

    Session session = Session.getInstance(getMailProperties(), new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("info@justmypart.eu", MAIL_SERVER_PASSWORD);
        }
    });

    public void sendConfirmationMail(Reservation reservation, User user) throws MessagingException, IOException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("info@justmypart.eu"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
        message.setSubject("RESERVATION CONFIRMATION");

        String msg = String.format("""
        Hello %s, <br><br>
        Your reservation has been confirmed! Here are the details:<br><br>
        
        <b>Destination:</b> %s <br>
        <b>Description:</b> %s <br>
        <b>Departure date:</b> %s <br>
        <b>Return date:</b> %s <br>
        <b>Number of people:</b> %d <br>
        <b>Email:</b> %s <br>
        <b>Price:</b> %s <br>
        """,    user.getFirstName(),
                reservation.getDestination().getName(),
                reservation.getDestination().getDescription(),
                reservation.getDestination().getDateOfDeparture(),
                reservation.getDestination().getDateOfReturn(),
                reservation.getNumberOfPeople(),
                user.getEmail(), // Fix: Added missing email argument
                reservation.getTotalPrice());

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

//    MimeBodyPart attachmentBodyPart = new MimeBodyPart();
//    attachmentBodyPart.attachFile(generateReservationPdf(reservation, user));
//    multipart.addBodyPart(attachmentBodyPart);

        message.setContent(multipart);
        Transport.send(message);
    }

    public void sendCancelationMail(Reservation reservation, User user) throws MessagingException, IOException {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("info@justmypart.eu"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
        message.setSubject("RESERVATION CANCELED");

        String msg = String.format("""
        Hello %s, <br><br>
        Your reservation has been canceled! Here are the details of the canceled reservation:<br><br>
        
        <b>Destination:</b> %s <br>
        <b>Description:</b> %s <br>
        <b>Departure date:</b> %s <br>
        <b>Return date:</b> %s <br>
        <b>Number of people:</b> %d <br>
        <b>Email:</b> %s <br>
        <b>Price:</b> %s <br>
        """,    user.getFirstName(),
                reservation.getDestination().getName(),
                reservation.getDestination().getDescription(),
                reservation.getDestination().getDateOfDeparture(),
                reservation.getDestination().getDateOfReturn(),
                reservation.getNumberOfPeople(),
                user.getEmail(), // Fix: Added missing email argument
                reservation.getTotalPrice());

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

//    MimeBodyPart attachmentBodyPart = new MimeBodyPart();
//    attachmentBodyPart.attachFile(generateReservationPdf(reservation, user));
//    multipart.addBodyPart(attachmentBodyPart);

        message.setContent(multipart);
        Transport.send(message);
    }

    private File generateReservationPdf(Reservation reservation, User user) throws MessagingException, IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        Document document = new Document(new PdfDocument(writer));
        document.add(new Paragraph("Reservation Confirmation"));
        document.add(new Paragraph("Client: " + user.getFirstName() + " " + user.getLastName()));
        document.add(new Paragraph("Email: " + user.getEmail()));
        document.add(new Paragraph("Destination: " + reservation.getDestination().getName()));
        document.add(new Paragraph("Number of people: " + reservation.getNumberOfPeople()));
        document.add(new Paragraph("Price: " + reservation.getTotalPrice() + "lv"));
        document.close();
        File file = new File(String.format("reservation_%d.pdf", reservation.getId())); // save it somewhere else...
        byteArrayOutputStream.writeTo(new FileOutputStream(file));
        return file;
    }

    private Properties getMailProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "mail.justmypart.eu");
        prop.put("mail.smtp.port", "587");
        return prop;
    }
}
