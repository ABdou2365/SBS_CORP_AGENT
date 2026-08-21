package com.nexacorp.ai.retrieval;

import com.nexacorp.ai.chunking.model.Chunk;
import com.nexacorp.ai.retrieval.model.RetrievalResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RetrievalService {

    private final VectorStore vectorStore;
    private final ChunkRankingComparator rankingComparator = new ChunkRankingComparator();

    public RetrievalService(@Qualifier("customVectorStore") VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public RetrievalResult retrieve(String query) {
        log.info("Retrieval requested for query: {}", query);

        SearchRequest searchRequest = SearchRequest.builder()
                .query(query)
                .topK(10)
                .build();

        List<Document> documents = vectorStore.similaritySearch(searchRequest);

        log.info("Vector store returned {} documents", documents.size());

        List<Chunk> chunks = documents.stream()
                .filter(this::isAllowedByMetadata)
                .map(this::toChunk)
                .sorted(rankingComparator)
                .collect(Collectors.toList());

        return new RetrievalResult(chunks);
    }

    private boolean isAllowedByMetadata(Document document) {
        Map<String, Object> metadata = document.getMetadata();
        String source = metadata.get("source").toString();

        if (!"DB".equals(source)) {
            return true;
        }
        String table = metadata.get("table").toString();
        return switch(table) {
            case "announcements" -> isActiveAnnouncement(metadata);
            case "faqs" -> isPublicFaq(metadata);
            case "release_notes" -> true;
            default -> true;
        };
    }

    private boolean isPublicFaq(Map<String, Object> metadata) {
        return !"RESTRICTED".equals(
                metadata.get("visibility").toString()
        );
    }

    private boolean isActiveAnnouncement(Map<String, Object> metadata) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

        String fromDate = metadata.get("effectiveFrom").toString();
        String tillDate = metadata.get("effectiveTo").toString();

        LocalDate from = LocalDate.parse(fromDate, formatter);
        LocalDate to = !tillDate.isEmpty() ? LocalDate.parse(tillDate, formatter) : today.plusDays(1);

        return !today.isBefore(from) && !today.isAfter(to);
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
