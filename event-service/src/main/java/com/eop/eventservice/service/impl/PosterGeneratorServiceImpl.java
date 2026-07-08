package com.eop.eventservice.service.impl;

import com.eop.eventservice.entity.Event;
import com.eop.eventservice.service.PosterGeneratorService;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;

@Service
@AllArgsConstructor
public class PosterGeneratorServiceImpl implements PosterGeneratorService {

    private final SpringTemplateEngine templateEngine;

    @Override
    public String generate(Event event) throws IOException {
        Context context = new Context();
        context.setVariable("eventTitle", event.getTitle());
        context.setVariable("churchName", "GMAHK Tangerang Palem Semi");
        context.setVariable("liturgyList", event.getParticipantData());
        context.setVariable("eventDate", formatEventTime(event.getEventStartTime(), event.getEventEndTime()));
        String base64LogoCache = null;

        try {
            byte[] fileContent = Files.readAllBytes(Paths.get(this.getClass().getResource("/static/logo-advent.jpeg").toURI()));
            base64LogoCache = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(fileContent);
        } catch (Exception e) {
            throw new IOException("Failed to Generate Logo Advent", e);
        }

        context.setVariable("logoPath", base64LogoCache);
        String html = templateEngine.process("poster/KebaktianSabatHTML", context);

        Path pdf = htmlToPdf(html, event);
        return pdfToPng(pdf);
    }

    private static String formatEventTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (startDateTime == null || endDateTime == null) {
            return "";
        }

        DateTimeFormatter dateWithDayFormatter = DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy", new Locale("id", "ID"));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH.mm");

        String datePart = startDateTime.format(dateWithDayFormatter);
        String startTimePart = startDateTime.format(timeFormatter);
        String endTimePart = endDateTime.format(timeFormatter);

        if (datePart.startsWith("Sabtu")) {
            datePart = datePart.replaceFirst("Sabtu", "Sabat");
        }

        return String.format("%s (Jam %s - %s)", datePart, startTimePart, endTimePart);
    }

    private Path htmlToPdf(String html, Event event) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateFolder = event.getEventStartTime().format(formatter);
        Path folderPath = Paths.get("poster", dateFolder);
        Files.createDirectories(folderPath);

        String fileName = event.getEventType() + LocalDate.now().format(formatter) + ".pdf";
        Path pdf = folderPath.resolve(fileName);

        try {
            Files.createDirectories(pdf.getParent());

            ITextRenderer renderer = new ITextRenderer();

            renderer.setDocumentFromString(html);
            renderer.layout();

            try (OutputStream os = Files.newOutputStream(pdf)) {
                renderer.createPDF(os);
            }

            return pdf;
        } catch(Exception e) {
            throw new RuntimeException("Generate PDF failed", e);
        }
    }

    private String pdfToPng(Path pdf) {
        Path image = Paths.get(pdf.toString().replace(".pdf",".png"));

        try (PDDocument document = Loader.loadPDF(pdf.toFile())) {
            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage img = renderer.renderImageWithDPI(0, 150);
            ImageIO.write(img, "png", image.toFile());
            return image.toString();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}