package com.nexacorp.ai.lifecycle;

import com.nexacorp.ai.lifecycle.model.KnowledgeRequest;
import com.nexacorp.ai.lifecycle.model.SourceType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class KnowledgeLifecycleServiceTest {

    @Autowired
    private KnowledgeLifecycleService knowledgeLifecycleService;

    @Test
    void ingest() {
        KnowledgeRequest pdfRequest = KnowledgeRequest.builder().fileName("test")
                .sourceType(SourceType.PDF).build();

        String pdfIdentity = KnowledgeIdentity.from(pdfRequest);
        log.info("Identity: {}", pdfIdentity);

        KnowledgeRequest dbRequest = KnowledgeRequest.builder().fileName("faqs")
                .sourceType(SourceType.DATABASE).build();

        String dbIdentity = KnowledgeIdentity.from(dbRequest);
        log.info("Identity: {}", dbIdentity);
    }

    @Test
    void delete() {


    }

    @Test
    void deleteAll() {
        knowledgeLifecycleService.deleteAll();
    }
}