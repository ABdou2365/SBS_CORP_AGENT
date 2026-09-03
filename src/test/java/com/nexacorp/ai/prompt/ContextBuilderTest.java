package com.nexacorp.ai.prompt;

import com.nexacorp.ai.prompt.model.PromptContext;
import com.nexacorp.ai.retrieval.RetrievalService;
import com.nexacorp.ai.retrieval.model.RetrievalResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ContextBuilderTest {

    @Autowired
    private RetrievalService retrievalService;

    @Test
    void contextLoads() {

        System.out.println("GEMINI_API_KEY exists: "
                + (System.getenv("GEMINI_API_KEY") != null));

        System.out.println("GEMINI_API_KEY length: "
                + (System.getenv("GEMINI_API_KEY") != null
                ? System.getenv("GEMINI_API_KEY").length()
                : 0));

        RetrievalResult chunks =
                retrievalService.retrieve(
                        "What is the work from home policy"
                );

        PromptContext context =
                new ContextBuilder().build(chunks);

        log.info("Context Builder Result: {}", context.getPromptText());
    }
}