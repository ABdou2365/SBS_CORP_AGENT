package com.nexacorp.ai.prompt;

import com.nexacorp.ai.prompt.model.SystemInstructions;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SystemPromptLoader {

    private static final String SYSTEM_PROMPT_PATH = "prompts/system-prompt.st";

    public SystemInstructions load() {
        try {
            // Represents the prompt file on the application's classpath; it is not the
            // prompt text itself.
            ClassPathResource resource = new ClassPathResource(SYSTEM_PROMPT_PATH);

            // Read the file's raw bytes and decode them as UTF-8 to obtain the actual
            // prompt text required by SystemInstructions.
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
