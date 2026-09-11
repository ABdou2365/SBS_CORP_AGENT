package com.nexacorp.ai.ingestion;

import com.nexacorp.ai.ingestion.db.DatabaseIngestionService;
import com.nexacorp.ai.ingestion.model.IngestedDocument;
import com.nexacorp.ai.ingestion.pdf.PdfIngestionService;
import com.nexacorp.ai.ingestion.wiki.WikiIngestionService;
import com.nexacorp.ai.lifecycle.model.SourceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IngestionOrchestrator {

    private final PdfIngestionService pdfIngestionService;
    private final WikiIngestionService wikiIngestionService;
    private final DatabaseIngestionService databaseIngestionService;

    public List<IngestedDocument> ingestBySourceType(SourceType sourceType, String fileName) throws Exception {
        switch (sourceType) {
            case PDF:
                return List.of(pdfIngestionService.ingest(fileName));
            case WIKI:
                return List.of(wikiIngestionService.ingest(fileName));
            case DATABASE:
                return databaseIngestionService.ingestedDocument(fileName);
            default:
                throw new IllegalArgumentException("Unsupported source type: " + sourceType);
        }
    }

    public List<IngestedDocument> ingestAll() throws  Exception {
        List<IngestedDocument> docs = new ArrayList<>();
        docs.addAll(pdfIngestionService.ingestPdfs());
        docs.addAll(wikiIngestionService.ingestWikis());
        docs.addAll(databaseIngestionService.ingestDatabaseContent());
        return docs;
    }

}
