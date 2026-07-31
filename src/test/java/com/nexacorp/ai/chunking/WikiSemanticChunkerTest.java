package com.nexacorp.ai.chunking;

import com.nexacorp.ai.chunking.model.Chunk;
import com.nexacorp.ai.ingestion.IngestionOrchestrator;
import com.nexacorp.ai.ingestion.model.IngestedDocument;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class WikiSemanticChunkerTest {
    private static final Logger log = LoggerFactory.getLogger(WikiSemanticChunkerTest.class);
    @Autowired
    private IngestionOrchestrator ingestionOrchestrator;

    @Autowired
    private FixedSizeChunker chunker;

    @Autowired
    private WikiSemanticChunker wikiSemanticChunker;

    @Test
    public void testChunker() throws Exception {
        List<IngestedDocument> documents = ingestionOrchestrator.ingestAll();

        // pick a wiki document
        IngestedDocument wikiDoc = documents.stream()
                .filter(d -> d.getSource().contains("WIKI"))
                .findFirst()
                .orElseThrow();

        log.info("========== FIXED SIZE CHUNKING ==========");
        List<Chunk> fixedChunks = chunker.chunk(wikiDoc, 500, 100);
        printChunks(wikiDoc, fixedChunks);

        log.info("========== SEMANTIC (WIKI) CHUNKING ==========");
        List<Chunk> semanticChunks = wikiSemanticChunker.chunk(wikiDoc);
        printChunks(wikiDoc, semanticChunks);
    }


    private static void printChunks(IngestedDocument document, List<Chunk> chunks) {
        log.info("Source: {}", document.getSource());
        log.info("Original length: {}", document.getContent().length());
        log.info("Total chunks: {}", chunks.size());

        for (Chunk chunk : chunks) {
            log.info("---- Chunk {} ----", chunk.getChunkIndex());
            log.info(chunk.getContent());
        }
    }
}
