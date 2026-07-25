package com.nexacorp.ai.ingestion.pdf;

import com.nexacorp.ai.ingestion.model.IngestedDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PdfIngestionService {

    private static final Logger log = LoggerFactory.getLogger(PdfIngestionService.class);
    private static final String PDF_DIRECTORY = "src/main/java/com/nexacorp/ai/data/pdfs";

    public List<IngestedDocument> ingestPdfs() throws Exception {
        File[] pdfFiles = new File(PDF_DIRECTORY).listFiles();
        List<IngestedDocument> docs = new ArrayList<>();

        if (pdfFiles == null) {
            throw new IOException("PDF directory does not exist or cannot be read: " + PDF_DIRECTORY);
        }
        for (File pdfFile : pdfFiles) {
            docs.add(ingestSinglePdf(pdfFile));
        }
        return docs;
    }

    private IngestedDocument ingestSinglePdf(File pdfFile) throws IOException {
        log.info("Ingesting PDF: {}", pdfFile.getName());

        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            log.info("----- Extracted Text ({}) -----", pdfFile.getName());
            log.info(text);
            return new IngestedDocument(
                    "PDF",
                    text,
                    Map.of("filename",pdfFile.getName()));
        }
    }
}
