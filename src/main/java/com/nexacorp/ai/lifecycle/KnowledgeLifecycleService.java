package com.nexacorp.ai.lifecycle;

import com.nexacorp.ai.chunking.ChunkingOrchestrator;
import com.nexacorp.ai.chunking.model.Chunk;
import com.nexacorp.ai.ingestion.IngestionOrchestrator;
import com.nexacorp.ai.ingestion.db.DatabaseIngestionService;
import com.nexacorp.ai.ingestion.model.IngestedDocument;
import com.nexacorp.ai.ingestion.pdf.PdfIngestionService;
import com.nexacorp.ai.ingestion.wiki.WikiIngestionService;
import com.nexacorp.ai.lifecycle.model.KnowledgeRequest;
import com.nexacorp.ai.vectorstore.ChunkVectorStoreService;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class KnowledgeLifecycleService {


    private final ChunkVectorStoreService vectorStore;
    private final IngestionOrchestrator ingestionOrchestrator;
    private final ChunkingOrchestrator chunkingOrchestrator;

    public KnowledgeLifecycleService(ChunkVectorStoreService vectorStore, DatabaseIngestionService databaseIngestionService, WikiIngestionService wikiIngestionService, PdfIngestionService pdfIngestionService, ChunkingOrchestrator chunkingOrchestrator, IngestionOrchestrator ingestionOrchestrator, ChunkingOrchestrator chunkingOrchestrator1) {
        this.vectorStore = vectorStore;
        this.ingestionOrchestrator = ingestionOrchestrator;
        this.chunkingOrchestrator = chunkingOrchestrator1;
    }

    public void ingest(KnowledgeRequest knowledgeRequest) throws Exception {
        // Implement the ingestion logic based on the source type and file name
        // For example, you can call different services based on the source type

        String identity = KnowledgeIdentity.from(knowledgeRequest);
        this.delete(identity); // Delete existing knowledge with the same identity before ingesting new content
        List<IngestedDocument> documents = ingestionOrchestrator.ingestBySourceType(knowledgeRequest.getSourceType(), knowledgeRequest.getFileName());
        documents.forEach(document -> {
            try {
                List<Chunk> chunks = chunkingOrchestrator.chunk(document);
                vectorStore.store(chunks);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


    }


    public void delete(String identity) {
        vectorStore.deleteByIdentity(identity);
    }


    public void deleteAll() {
        vectorStore.deleteAll();
    }

}
