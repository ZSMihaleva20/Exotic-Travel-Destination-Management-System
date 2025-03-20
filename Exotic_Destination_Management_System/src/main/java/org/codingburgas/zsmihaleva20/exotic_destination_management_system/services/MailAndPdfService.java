package org.codingburgas.zsmihaleva20.exotic_destination_management_system.services;


import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Destination;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.Reservation;
import org.codingburgas.zsmihaleva20.exotic_destination_management_system.models.User;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class MailAndPdfService {

    private final static String MAIL_SERVER_PASSWORD = "i,YFfe7yh=jC";
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.forLanguageTag("bg"));

    // Email session configuration
    Session session = Session.getInstance(getMailProperties(), new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("info@justmypart.eu", MAIL_SERVER_PASSWORD);
        }
    });

    // Send confirmation email for a reservation
    public void sendConfirmationMail(Reservation reservation, User user) throws MessagingException, IOException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("info@justmypart.eu"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
        message.setSubject("Нова резервация");

        // Format departure and return dates
        String formattedDepartureDate = reservation.getDestination().getDateOfDeparture().format(dateFormatter);
        String formattedReturnDate = reservation.getDestination().getDateOfReturn().format(dateFormatter);

        String msg = String.format("""
    Здравей %s, <br><br>
    Твоята резервация беше успешно направена! <br><br> Детайли за резервацията:<br><br>
    
    <b>Дестинация:</b> %s <br>
    <b>Дата на заминаване:</b> %s <br>
    <b>Дата на връщане:</b> %s <br>
    <b>Брой хора:</b> %d <br>
    <b>Цена:</b> %.2f лв. <br>
    """,    user.getFirstName(),
                reservation.getDestination().getName(),
                formattedDepartureDate,  // Use formatted date
                formattedReturnDate,     // Use formatted date
                reservation.getNumberOfPeople(),
                reservation.getTotalPrice());

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);
        Transport.send(message);
    }

    // Send cancellation email for a reservation
    public void sendCancelationMail(Reservation reservation, User user) throws MessagingException, IOException {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("info@justmypart.eu"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
        message.setSubject("Отказана резервация");

        // Format departure and return dates
        String formattedDepartureDate = reservation.getDestination().getDateOfDeparture().format(dateFormatter);
        String formattedReturnDate = reservation.getDestination().getDateOfReturn().format(dateFormatter);

        String msg = String.format("""
    Здравей %s, <br><br>
    Твоята резервация беше отказана! <br><br> Детайли за отказаната дестинация:<br><br>
    
    <b>Дестинация:</b> %s <br>
    <b>Дата на заминаване:</b> %s <br>
    <b>Дата на връщане:</b> %s <br>
    <b>Брой хора:</b> %d <br>
    <b>Цена:</b> %.2f лв. <br>
    """,    user.getFirstName(),
                reservation.getDestination().getName(),
                formattedDepartureDate,  // Use formatted date
                formattedReturnDate,     // Use formatted date
                reservation.getNumberOfPeople(),
                reservation.getTotalPrice());

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);
        Transport.send(message);
    }

    // Generate a PDF with all active reservations
    public byte[] generateAllActiveReservationsPdf(List<Reservation> activeReservations) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        // Load a font that supports Cyrillic characters
        PdfFont font = PdfFontFactory.createFont("/fonts/times.ttf", PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);

        // Title
        document.add(new Paragraph("Активни резервации")
                .setFont(font)
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20));

        // Formatter for date
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.forLanguageTag("bg"));

        // Group reservations by destination
        Map<Destination, List<Reservation>> reservationsByDestination = activeReservations.stream()
                .collect(Collectors.groupingBy(Reservation::getDestination));

        if (reservationsByDestination.isEmpty()) {
            document.add(new Paragraph("Няма активни резервации.").setFont(font).setFontSize(14));
        }

        for (Map.Entry<Destination, List<Reservation>> entry : reservationsByDestination.entrySet()) {
            Destination destination = entry.getKey();
            List<Reservation> reservations = entry.getValue();

            // Format departure and return dates
            String formattedDepartureDate = destination.getDateOfDeparture().format(dateFormatter);
            String formattedReturnDate = destination.getDateOfReturn().format(dateFormatter);
            String formattedPrice = String.format("%.2f", destination.getPrice());

            // Destination details
            document.add(new Paragraph("\nДестинация: " + destination.getName()).setFont(font).setFontSize(14));
            document.add(new Paragraph("Дата на заминаване: " + formattedDepartureDate).setFont(font));
            document.add(new Paragraph("Дата на връщане: " + formattedReturnDate).setFont(font));
            document.add(new Paragraph("Цена: " + formattedPrice + " лв. за 1 човек").setFont(font));
            document.add(new Paragraph("Максимален капацитет: " + destination.getLimitedPeople()).setFont(font));
            document.add(new Paragraph("Налични места: " + destination.getRemainingPeople()).setFont(font));

            // Table for reservations under this destination
            Table table = new Table(new float[]{4, 4, 3, 2, 3});
            table.useAllAvailableWidth();

            // Table headers
            table.addHeaderCell(new Cell().add(new Paragraph("Клиент").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("Имейл").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("Брой хора").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("Статус").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("Обща цена").setFont(font)));

            // Add reservations data
            for (Reservation reservation : reservations) {
                User user = reservation.getUser();
                String formattedTotalPrice = String.format("%.2f", reservation.getTotalPrice());

                table.addCell(new Cell().add(new Paragraph(user.getFirstName() + " " + user.getLastName()).setFont(font)));
                table.addCell(new Cell().add(new Paragraph(user.getEmail()).setFont(font)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(reservation.getNumberOfPeople())).setFont(font)));
                table.addCell(new Cell().add(new Paragraph("ПЛАТЕНА").setFont(font)));
                table.addCell(new Cell().add(new Paragraph(formattedTotalPrice + " лв.").setFont(font)));
            }

            document.add(table);
        }

        document.close();
        return byteArrayOutputStream.toByteArray();
    }

    // SMTP server configuration
    private Properties getMailProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "mail.justmypart.eu");
        prop.put("mail.smtp.port", "587");
        return prop;
    }
}
