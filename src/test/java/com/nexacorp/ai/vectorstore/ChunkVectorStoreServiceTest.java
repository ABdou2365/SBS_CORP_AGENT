package com.nexacorp.ai.vectorstore;

import com.nexacorp.ai.chunking.ChunkingOrchestrator;
import com.nexacorp.ai.chunking.model.Chunk;
import com.nexacorp.ai.ingestion.IngestionOrchestrator;
import com.nexacorp.ai.ingestion.model.IngestedDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ChunkVectorStoreServiceTest {
    @Autowired
    private IngestionOrchestrator ingestionOrchestrator;

    @Autowired
    private ChunkingOrchestrator chunkingOrchestrator;

    @Autowired
    private ChunkVectorStoreService vectorStoreService;

    @Test
    void testVectorStore() throws Exception {
        List<IngestedDocument> documents = ingestionOrchestrator.ingestAll();

        List<Chunk> chunksToStore = new ArrayList<>();
        for (IngestedDocument document : documents) {
            List<Chunk> chunks = chunkingOrchestrator.chunk(document);
            chunksToStore.addAll(chunks);
        }
        vectorStoreService.store(chunksToStore);
    }
}
