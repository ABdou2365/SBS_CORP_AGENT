package com.nexacorp.ai.prompt;

import com.nexacorp.ai.prompt.model.SystemInstructions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.logging.Logger;

@SpringBootTest
public class SystemPromptLoaderTest {

    private static final Logger log = Logger.getLogger(SystemPromptLoaderTest.class.getName());

    @Test
    public void test() {
        SystemInstructions instructions = new SystemPromptLoader().load();

        log.info("Successfully loaded the system prompt");
        log.info("System prompt content: " + instructions.getSystemInstructions());
    }
}
