package com.nexacorp.ai.chunking;

import com.nexacorp.ai.chunking.model.Chunk;
import com.nexacorp.ai.ingestion.model.IngestedDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChunkingOrchestrator {

    private final WikiSemanticChunker wikiSemanticChunker;
    private final PdfPragmaticChunker pdfPragmaticChunker;
    private final DatabaseChunker databaseChunker;

    public List<Chunk> chunk(IngestedDocument document) throws Exception {
        return switch (document.getSource()) {
            case "WIKI" ->
                    wikiSemanticChunker.chunk(document);

            case "PDF" ->
                    pdfPragmaticChunker.chunk(document);

            case "DB" ->
                    databaseChunker.chunk(document);

            default ->
                    throw new IllegalArgumentException("Unsupported source type: " + document.getSource());
        };

    }
}
