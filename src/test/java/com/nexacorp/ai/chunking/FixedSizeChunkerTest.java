package com.nexacorp.ai.chunking;

import com.nexacorp.ai.chunking.model.Chunk;
import com.nexacorp.ai.ingestion.IngestionOrchestrator;
import com.nexacorp.ai.ingestion.model.IngestedDocument;
import com.nexacorp.ai.ingestion.pdf.PdfIngestionService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class FixedSizeChunkerTest {

    private static final Logger log = LoggerFactory.getLogger(FixedSizeChunkerTest.class);


    @Autowired
    IngestionOrchestrator ingestionOrchestrator;

    @Autowired
    FixedSizeChunker fixedSizeChunker;

    @Test
    public void fixedSizeChunkerTest() throws Exception {

        List<IngestedDocument> documents = ingestionOrchestrator.ingestAll();
        IngestedDocument firstIngestedDocument = documents.get(0);

        List<Chunk> chunks = fixedSizeChunker.chunk(firstIngestedDocument,500);

        log.info("----- Chunks for Document: {} -----", firstIngestedDocument.getSource());
        for (Chunk chunk : chunks) {
            log.info("Chunk source: {}", chunk.getSource());
            log.info("Chunk index: {}", chunk.getChunkIndex());
            log.info("Chunk content: {}", chunk.getContent());
        }

    }
}
