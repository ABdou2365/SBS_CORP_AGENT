package com.nexacorp.ai.ingestion.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DatabaseIngestionServiceTest {

    @Autowired
    DatabaseIngestionService databaseIngestionService;

    @Test
    void ingestAll(){
        ingestFaqs();
        ingestAnnouncements();
        ingestReleaseNotes();
    }

    @Test
    void ingestFaqs() {
        databaseIngestionService.ingestFaqs();
    }

    @Test
    void ingestReleaseNotes() {
        databaseIngestionService.ingestReleaseNotes();
    }

    @Test
    void ingestAnnouncements() {
        databaseIngestionService.ingestAnnouncements();
    }

}
