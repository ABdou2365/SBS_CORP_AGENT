package com.nexacorp.ai.prompt.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatPrompt {
    private final SystemInstructions systemInstructions;
    private final PromptContext context;
    private final String groundingRule;
}
