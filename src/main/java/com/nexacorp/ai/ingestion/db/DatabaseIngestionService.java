package com.nexacorp.ai.ingestion.db;

import com.nexacorp.ai.ingestion.model.IngestedDocument;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DatabaseIngestionService {

    private static final Logger log = LoggerFactory.getLogger(DatabaseIngestionService.class);
    private final JdbcTemplate jdbcTemplate;

    public List<IngestedDocument> ingestDatabaseContent() {
        List<IngestedDocument> docs = new ArrayList<>();
        docs.addAll(ingestFaqs());
        docs.addAll(ingestReleaseNotes());
        docs.addAll(ingestAnnouncements());

        return docs;
    }

    public List<IngestedDocument> ingestFaqs() {
        List<Map<String, Object>> rows =
                jdbcTemplate.queryForList(
                        "SELECT id, question, answer, department, visibility FROM faqs");

        List<IngestedDocument> docs = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            log.info("----- FAQ -----");
            log.info("Q: {}", row.get("question"));
            log.info("A: {}", row.get("answer"));

            String content = "Question: " + row.get("question") + "\n" +
                    "Answer: " + row.get("answer");

            docs.add(new IngestedDocument(
                    "DB",
                    content,
                    Map.of("table", "faqs",
                            "id", row.get("id"),
                            "department", row.get("department"),
                            "visibility", row.get("visibility"),
                            "identity","DB#faqs"
                    )
            ));
        }
        return docs;
    }

    public List<IngestedDocument> ingestReleaseNotes() {
        List<Map<String, Object>> rows =
                jdbcTemplate.queryForList(
                        "SELECT id, version, summary, details, release_date FROM release_notes");

        List<IngestedDocument> docs = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            log.info("----- Release Note -----");
            log.info("Version: {}", row.get("version"));
            log.info("Description: {}", row.get("summary"));


            String content = "Version: " + row.get("version") + "\n" +
                    "Summary: " + row.get("summary") + "\n" +
                    "Details: " + row.get("details");

            docs.add(new IngestedDocument(
                    "DB",
                    content,
                    Map.of("table", "release_notes",
                            "id", row.get("id"),
                            "version", row.get("version"),
                            "releaseDate", row.get("release_date"),
                            "identity", "DB#release_notes"
                    )
            ));
        }
        return docs;
    }

    public List<IngestedDocument> ingestAnnouncements() {
        List<Map<String, Object>> rows =
                jdbcTemplate.queryForList("SELECT id, subject, body, category," +
                        "effective_from, effective_to, source_type FROM announcements");

        List<IngestedDocument> docs = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            log.info("----- Announcement -----");
            log.info("Title: {}", row.get("subject"));

            String content = "Subject: " + row.get("subject") + "\n" + row.get("body");
            docs.add(new IngestedDocument(
                    "DB",
                    content,
                    Map.of(
                            "table", "announcements",
                            "id", row.get("id"),
                            "category", row.get("category"),
                            "effectiveFrom", row.get("effective_from"),
                            "effectiveTo", row.get("effectiveTo") != null ? row.get("effectiveTo") : "",
                            "sourceType", row.get("source_type"),
                            "identity", "DB#announcements"
                    )
            ));
        }
        return docs;
    }
}
