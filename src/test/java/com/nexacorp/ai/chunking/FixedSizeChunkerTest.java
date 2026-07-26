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
    private IngestionOrchestrator ingestionOrchestrator;

    @Autowired
    private FixedSizeChunker chunker;

    @Test
    public void chunkerTest() throws Exception {
        List<IngestedDocument> documents = ingestionOrchestrator.ingestAll();

        IngestedDocument document = documents.get(0);

        /* WITH NO OVERLAP */
        log.info("----- Chunking with no overlap -----");
        List<Chunk> chunks = chunker.chunk(document, 500);
        logIngestedChunks(document, chunks);

        log.info("----- Chunking with overlap -----");
        List<Chunk> chunksOverlaps = chunker.chunk(document, 500,100);
        logIngestedChunks(document, chunksOverlaps);

    }

    private static void logIngestedChunks(IngestedDocument document, List<Chunk> chunks) {
        log.info("Source: {}", document.getSource());
        log.info("Original length: {}", document.getContent().length());
        log.info("Total chunks: {}", chunks.size());

        for (Chunk chunk : chunks) {
            log.info("---- Chunk {} ----", chunk.getChunkIndex());
            log.info(chunk.getContent());
        }
    }
}
