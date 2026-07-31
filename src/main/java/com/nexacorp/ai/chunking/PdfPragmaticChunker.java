package com.nexacorp.ai.chunking;

import com.nexacorp.ai.chunking.model.Chunk;
import com.nexacorp.ai.ingestion.model.IngestedDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PdfPragmaticChunker {

    private static final int PDF_CHUNK_SIZE = 500;
    private static final int PDF_CHUNK_OVERLAP = 100;

    private final FixedSizeChunker fixedSizeChunker;

    public List<Chunk> chunk(IngestedDocument document) {

        List<Chunk> rawChunks = fixedSizeChunker.chunk(document, PDF_CHUNK_SIZE, PDF_CHUNK_OVERLAP);

        return rawChunks.stream()
                .map(this::enrichPdfMetadata)
                .collect(Collectors.toList());
    }

    private Chunk enrichPdfMetadata(Chunk chunk) {
        Map<String, Object> enrichedMetadata = new HashMap<>(chunk.getMetadata());

        enrichedMetadata.put("sourceType", "PDF");
        enrichedMetadata.put("chunkStrategy", "PDF_PRAGMATIC_FIXED_SIZE");

        enrichedMetadata.put("chunkSize", PDF_CHUNK_SIZE);
        enrichedMetadata.put("chunkOverlap", PDF_CHUNK_OVERLAP);

        return new Chunk(
                chunk.getSource(),
                chunk.getContent(),
                enrichedMetadata,
                chunk.getChunkIndex()
        );
    }
}
