package org.codingburgas.zsmihaleva20.exotic_destination_management_system.services;


import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Reservation;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.User;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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


    public byte[] generateReservationPdf(Reservation reservation, User user) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.forLanguageTag("bg"));

        // Load font that supports UTF-8 and Cyrillic (Noto Sans recommended)
        PdfFont utf8Font = PdfFontFactory.createFont("C:/Windows/Fonts/times.ttf");

        // Define colors
        DeviceRgb beigeColor = new DeviceRgb(245, 245, 220);
        DeviceRgb blackColor = new DeviceRgb(0, 0, 0);

        // Header
        document.add(new Paragraph("Информация за резервацията")  // Bulgarian text for "Reservation Information"
                .setFont(utf8Font)
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20));

        // Client Information
        Table clientTable = new Table(UnitValue.createPercentArray(new float[]{1, 2}))
                .useAllAvailableWidth()
                .setMarginBottom(10);

        clientTable.addCell(new Cell().add(new Paragraph("Клиент:").setFont(utf8Font)).setBorder(Border.NO_BORDER));
        clientTable.addCell(new Cell().add(new Paragraph(user.getFirstName() + " " + user.getLastName()).setFont(utf8Font)).setBorder(Border.NO_BORDER));

        clientTable.addCell(new Cell().add(new Paragraph("Имейл:").setFont(utf8Font)).setBorder(Border.NO_BORDER));
        clientTable.addCell(new Cell().add(new Paragraph(user.getEmail()).setFont(utf8Font)).setBorder(Border.NO_BORDER));

        document.add(clientTable);

        // Reservation Details
        Table detailsTable = new Table(UnitValue.createPercentArray(new float[]{1, 2}))
                .useAllAvailableWidth()
                .setBorder(new SolidBorder(blackColor, 1))
                .setMarginBottom(20);

        detailsTable.addCell(new Cell().add(new Paragraph("Дестинация:").setFont(utf8Font)).setBackgroundColor(beigeColor).setBorder(new SolidBorder(blackColor, 1)));
        detailsTable.addCell(new Cell().add(new Paragraph(reservation.getDestination().getName()).setFont(utf8Font)).setBorder(new SolidBorder(blackColor, 1)));

        detailsTable.addCell(new Cell().add(new Paragraph("Брой хора:").setFont(utf8Font)).setBackgroundColor(beigeColor).setBorder(new SolidBorder(blackColor, 1)));
        detailsTable.addCell(new Cell().add(new Paragraph(String.valueOf(reservation.getNumberOfPeople())).setFont(utf8Font)).setBorder(new SolidBorder(blackColor, 1)));

        detailsTable.addCell(new Cell().add(new Paragraph("Цена:").setFont(utf8Font)).setBackgroundColor(beigeColor).setBorder(new SolidBorder(blackColor, 1)));
        detailsTable.addCell(new Cell().add(new Paragraph(reservation.getTotalPrice() + " лв").setFont(utf8Font)).setBorder(new SolidBorder(blackColor, 1)));

        detailsTable.addCell(new Cell().add(new Paragraph("Дата на заминаване:").setFont(utf8Font)).setBackgroundColor(beigeColor).setBorder(new SolidBorder(blackColor, 1)));
        detailsTable.addCell(new Cell().add(new Paragraph(reservation.getDestination().getDateOfDeparture().format(dateFormatter)).setFont(utf8Font)).setBorder(new SolidBorder(blackColor, 1)));

        detailsTable.addCell(new Cell().add(new Paragraph("Дата на връщане:").setFont(utf8Font)).setBackgroundColor(beigeColor).setBorder(new SolidBorder(blackColor, 1)));
        detailsTable.addCell(new Cell().add(new Paragraph(reservation.getDestination().getDateOfReturn().format(dateFormatter)).setFont(utf8Font)).setBorder(new SolidBorder(blackColor, 1)));

        document.add(detailsTable);

        // Description with Bullet Points (Ensuring Bulgarian characters work)
        if (reservation.getDestination().getDescription() != null && !reservation.getDestination().getDescription().isEmpty()) {
            document.add(new Paragraph("Описание:").setFont(utf8Font).setMarginBottom(5));

            List descriptionList = new List();
            String[] bulletPoints = reservation.getDestination().getDescription().split("\\.");
            for (String point : bulletPoints) {
                if (!point.trim().isEmpty()) {
                    descriptionList.add((ListItem) new ListItem(point.trim() + ".").setFont(utf8Font));
                }
            }
            document.add(descriptionList);
        }

        // Footer
        document.add(new Paragraph("Благодарим Ви, че направихте резервация при нас!")  // "Thank you for booking with us!"
                .setFont(utf8Font)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(20));

        document.close();

        // Save file
        return byteArrayOutputStream.toByteArray();
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
