package com.nexacorp.ai.retrieval;

import com.nexacorp.ai.chunking.model.Chunk;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;

public class ChunkRankingComparator implements Comparator<Chunk>  {
    @Override
    public int compare(Chunk a, Chunk b) {

        // 1. source priority
        int sourceCompare = Integer.compare(sourcePriority(a), sourcePriority(b));
        if (sourceCompare != 0) {
            return sourceCompare;
        }

        // 2. DB table priority
        if (isDbChunk(a) && isDbChunk(b)) {
            int tableCompare = Integer.compare(tablePriority(a), tablePriority(b));
            if (tableCompare != 0) {
                return tableCompare;
            }
        }


        // 3. Recency
        LocalDate dateA = extractRelevantDate(a);
        LocalDate dateB = extractRelevantDate(b);
        if (dateA != null && dateB != null) {
            return dateB.compareTo(dateA); // newer first
        }

        return 0;
    }

    private LocalDate extractRelevantDate(Chunk chunk) {
        Map<String, Object> metadata = chunk.getMetadata();
        if (!"DB".equals(metadata.get("source"))) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

        String table = metadata.get("table").toString();
        if ("release_notes".equals(table)) {
            return LocalDate.parse(metadata.get("releaseDate").toString(), formatter);
        }

        if ("announcements".equals(table)) {
            return LocalDate.parse(metadata.get("effectiveFrom").toString(), formatter);
        }

        return null;
    }

    private int tablePriority(Chunk chunk) {
        String table = chunk.getMetadata().get("table").toString();

        return switch (table) {
            case "release_notes" -> 1;
            case "announcements" -> 2;
            case "faqs" -> 3;
            default -> 4;
        };
    }

    private boolean isDbChunk(Chunk chunk) {
        return "DB".equals(chunk.getMetadata().get("source"));
    }

    private int sourcePriority(Chunk chunk) {
        String source = chunk.getMetadata().get("source").toString();

        return switch (source) {
            case "PDF" -> 1;
            case "DB" -> 2;
            case "WIKI" -> 3;
            default -> 4;
        };
    }
}
