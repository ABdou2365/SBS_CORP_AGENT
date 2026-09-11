package com.nexacorp.ai.lifecycle;

import com.nexacorp.ai.lifecycle.model.KnowledgeRequest;
import com.nexacorp.ai.lifecycle.model.SourceType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
class KnowledgeLifecycleServiceTest {

    @Autowired
    private KnowledgeLifecycleService knowledgeLifecycleService;
    @Autowired
    private RedisVectorStore vectorStore;

    @Test
    void ingest() throws Exception {
        KnowledgeRequest pdfRequest = KnowledgeRequest.builder().fileName("HR_Leave_Policy.pdf")
                .sourceType(SourceType.PDF).build();

        String pdfIdentity = KnowledgeIdentity.from(pdfRequest);
        log.info("Identity: {}", pdfIdentity);
        knowledgeLifecycleService.ingest(pdfRequest);
    }

    @Test
    void delete() {
        KnowledgeRequest pdfRequest = KnowledgeRequest.builder().fileName("HR_Leave_Policy.pdf")
                .sourceType(SourceType.PDF).build();

        String pdfIdentity = KnowledgeIdentity.from(pdfRequest);
        log.info("Identity: {}", pdfIdentity);
        knowledgeLifecycleService.delete(pdfIdentity);

    }

    @Test
    void deleteAll() {
        knowledgeLifecycleService.deleteAll();
    }

    @Test
    void test(){
        RedisVectorStore redisVectorStore = (RedisVectorStore) vectorStore;

        long count = redisVectorStore.count(
                "@identity:{\"PDF#HR_Leave_Policy.pdf\"}"
        );

        System.out.println("Matching chunks = " + count);
    }
}