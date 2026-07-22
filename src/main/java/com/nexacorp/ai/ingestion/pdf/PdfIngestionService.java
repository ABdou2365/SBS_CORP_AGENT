package com.nexacorp.ai.ingestion.pdf;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PdfIngestionService {

    private static final Logger logger = LoggerFactory.getLogger(PdfIngestionService.class);

    private final URL pdfDirectoryUrl;

    public PdfIngestionService(
            @Value("${app.ingestion.pdf-directory-url:file:src/main/java/com/nexacorp/ai/data/pdfs/}") URL pdfDirectoryUrl) {
        this.pdfDirectoryUrl = pdfDirectoryUrl;
    }

    public void ingestPdfs() {
        try (Stream<Path> pdfFiles = Files.list(Path.of(pdfDirectoryUrl.toURI()))) {
            pdfFiles
                    .filter(Files::isRegularFile)
                    .filter(file -> file.getFileName().toString().toLowerCase().endsWith(".pdf"))
                    .forEach(pdfFile -> logger.info("Found PDF for ingestion: {}", pdfFile));
        } catch (IOException | URISyntaxException exception) {
            throw new IllegalStateException("Unable to read PDFs from " + pdfDirectoryUrl, exception);
        }
    }
}
