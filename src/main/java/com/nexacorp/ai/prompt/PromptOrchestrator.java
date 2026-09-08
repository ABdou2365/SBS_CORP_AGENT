package com.nexacorp.ai.prompt;

import com.nexacorp.ai.prompt.model.ChatPrompt;
import com.nexacorp.ai.prompt.model.PromptContext;
import com.nexacorp.ai.prompt.model.SystemInstructions;
import com.nexacorp.ai.retrieval.RetrievalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromptOrchestrator {


    private final RetrievalService retrievalService;
    private final ContextBuilder contextBuilder =  new ContextBuilder();
    private final GroundingPolicy groundingPolicy = new GroundingPolicy();
    private final SystemPromptLoader systemPromptLoader = new SystemPromptLoader();

    public ChatPrompt build(String userQuestion) {

        PromptContext promptContext = contextBuilder.build(retrievalService.retrieve(userQuestion));
        String groundingRule = groundingPolicy.groundingRules(promptContext);
        SystemInstructions systemInstructions = systemPromptLoader.load();

        return new ChatPrompt(systemInstructions, promptContext, groundingRule);
    }

}
