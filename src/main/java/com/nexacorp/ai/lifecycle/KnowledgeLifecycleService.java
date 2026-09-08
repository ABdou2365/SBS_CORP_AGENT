package com.nexacorp.ai.lifecycle;

import ch.qos.logback.core.util.FileUtil;
import com.nexacorp.ai.ingestion.db.DatabaseIngestionService;
import com.nexacorp.ai.ingestion.pdf.PdfIngestionService;
import com.nexacorp.ai.ingestion.wiki.WikiIngestionService;
import com.nexacorp.ai.lifecycle.model.KnowledgeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.nexacorp.ai.lifecycle.model.SourceType.*;

@Service
public class KnowledgeLifecycleService {


    private final PdfIngestionService pdfIngestionService;
    private final WikiIngestionService wikiIngestionService;
    private final DatabaseIngestionService databaseIngestionService;

    public KnowledgeLifecycleService(PdfIngestionService pdfIngestionService, WikiIngestionService wikiIngestionService, DatabaseIngestionService databaseIngestionService) {
        this.pdfIngestionService = pdfIngestionService;
        this.wikiIngestionService = wikiIngestionService;
        this.databaseIngestionService = databaseIngestionService;
    }

    public void ingest(KnowledgeRequest knowledgeRequest) throws Exception {
        // Implement the ingestion logic based on the source type and file name
        // For example, you can call different services based on the source type
        switch (knowledgeRequest.getSourceType()) {
            case PDF:
                // Call PDF ingestion service
                pdfIngestionService.ingestPdfs();
                break;
            case WIKI:
                // Call WIKI ingestion service
                wikiIngestionService.ingestWikis();
                break;
            case DATABASE:
                // Call Database ingestion service
                databaseIngestionService.ingestDatabaseContent();
                break;
            default:
                throw new IllegalArgumentException("Unsupported source type: " + knowledgeRequest.getSourceType());
        }
    }

    public void delete(KnowledgeRequest knowledgeRequest) {
        switch (knowledgeRequest.getSourceType()) {
            case PDF:
                break;
            case WIKI:
                break;
            case DATABASE:
                break;
            default:
                throw new IllegalArgumentException("Unsupported source type: " + knowledgeRequest.getSourceType());
        }
    }

}
