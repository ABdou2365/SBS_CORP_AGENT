package com.nexacorp.ai.chunking;

import com.nexacorp.ai.chunking.model.Chunk;
import com.nexacorp.ai.ingestion.model.IngestedDocument;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseChunker {

    public List<Chunk> chunk(IngestedDocument document) {
        return List.of(
                new Chunk(
                        document.getSource(),
                        document.getContent(),
                        document.getMetadata(),
                        0
                ));
    }
}
