package com.nexacorp.ai.prompt;

import com.nexacorp.ai.prompt.model.SystemInstructions;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SystemPromptLoader {

    private static final String SYSTEM_PROMPT_PATH = "prompts/system-prompt.st";

    public SystemInstructions load() {
        try {
            ClassPathResource resource = new ClassPathResource(SYSTEM_PROMPT_PATH);

            String prompt = new String(
                    resource.getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8
            );
            return new SystemInstructions(prompt);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to load system prompt from " + SYSTEM_PROMPT_PATH, ex);
        }
    }
}
