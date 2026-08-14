package com.nexacorp.ai.retrieval;

import com.nexacorp.ai.chunking.model.Chunk;
import com.nexacorp.ai.retrieval.model.RetrievalResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RetrievalService {

    private final VectorStore vectorStore;

    public RetrievalService(@Qualifier("customVectorStore") VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public RetrievalResult retrieve(String query) {
        log.info("Retrieval requested for query: {}", query);

        SearchRequest searchRequest = SearchRequest.builder()
                .query(query)
                .topK(5)
                .build();

        List<Document> documents = vectorStore.similaritySearch(searchRequest);

        log.info("Vector store returned {} documents", documents.size());

        List<Chunk> chunks = documents.stream()
                .map(this::toChunk)
                .collect(Collectors.toList());

        return new RetrievalResult(chunks);
    }

    private Chunk toChunk(Document document) {
        String content = document.getText();
        Map<String,Object> metadata = new HashMap<>(document.getMetadata());

        int chunkIndex = 0;
        Object chunkIndexValue = metadata.get("chunkIndex");
        if (chunkIndexValue != null) {
            chunkIndex = Integer.parseInt(chunkIndexValue.toString());
        }
        return new Chunk((String) metadata.get("source"),
                content,
                metadata,
                chunkIndex);
    }
}
