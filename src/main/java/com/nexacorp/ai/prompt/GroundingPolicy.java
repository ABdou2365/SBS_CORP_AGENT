package com.nexacorp.ai.prompt;

import com.nexacorp.ai.prompt.model.PromptContext;

public class GroundingPolicy {

    public String groundingRules(PromptContext context) {

        if (context == null || context.getPromptText().isBlank()) {
            return """
                   You do not have sufficient information to answer the question.
                   Respond clearly that you do not know.
                   Do not guess or infer.
                   """;
        }

        return """
               Answer the user's question using ONLY the provided context.
               If the answer is not present in the context, say that you do not know.
               Do not use prior knowledge.
               """;
    }
}
