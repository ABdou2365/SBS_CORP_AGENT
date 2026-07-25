package com.nexacorp.ai.ingestion.wiki;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class WikiIngestionServiceTest {

    @Autowired
    private WikiIngestionService wikiIngestionService;

    @Test
    public void wikiIngestionServiceTest() throws Exception {
        wikiIngestionService.ingestWikis();
    }
}

