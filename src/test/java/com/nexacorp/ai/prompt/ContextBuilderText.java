package com.nexacorp.ai.prompt;

import com.nexacorp.ai.retrieval.RetrievalService;
import com.nexacorp.ai.retrieval.model.RetrievalResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ContextBuilderText {

    @Autowired
    private RetrievalService retrievalService;

    @Test
    public void contextLoads() {
        RetrievalResult chunks = retrievalService.retrieve("Explain the nexacorp authentication flow");
        String contextBuilder = new ContextBuilder().build(chunks).toString();

        log.info("Context Builder Result: {}", contextBuilder);



    }
}
