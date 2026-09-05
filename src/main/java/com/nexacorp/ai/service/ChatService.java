package com.nexacorp.ai.service;

import com.nexacorp.ai.dto.ChatRequest;
import com.nexacorp.ai.dto.ChatResponse;
import com.nexacorp.ai.prompt.PromptOrchestrator;
import com.nexacorp.ai.prompt.model.ChatPrompt;
import com.nexacorp.ai.retrieval.RetrievalService;
import com.nexacorp.ai.retrieval.model.RetrievalResult;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;
    private final PromptOrchestrator promptOrchestrator;

    public ChatResponse chat(ChatRequest request) {
        String userMessage = request.getMessage();
        ChatPrompt chatPrompt = promptOrchestrator.build(userMessage);

        String llmInput = chatPrompt.getContext().getPromptText() +
                "\n\n" + chatPrompt.getGroundingRule()
                + "\n\n" + userMessage;

        String aiResponse = chatClient.prompt()
                .user(llmInput)
                .system(chatPrompt.getSystemInstructions().getSystemInstructions())
                .call().content();

        return new ChatResponse(aiResponse);
    }
}
