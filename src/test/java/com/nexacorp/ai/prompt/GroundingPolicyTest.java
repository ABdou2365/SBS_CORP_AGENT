package com.nexacorp.ai.prompt;

import com.nexacorp.ai.prompt.model.PromptContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class GroundingPolicyTest {

    @Test
    void emptyGroundingRules() {
        GroundingPolicy gp = new GroundingPolicy();
        String rule = gp.groundingRules(new PromptContext(""));
        log.info("--------EMPTY STRING--------");
        log.info("Empty Grounding rules {}", rule);
    }

    @Test
    void nonEmptyGroundingRules() {
        GroundingPolicy gp = new GroundingPolicy();
        String rule = gp.groundingRules(new PromptContext("some string context"));
        log.info("-------NON EMPTY STRING--------");
        log.info("Non Empty Grounding rules {}", rule);
    }
}