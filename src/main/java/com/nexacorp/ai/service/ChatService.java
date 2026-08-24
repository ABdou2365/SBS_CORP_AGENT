package com.nexacorp.ai.service;

import com.nexacorp.ai.dto.ChatRequest;
import com.nexacorp.ai.dto.ChatResponse;
import com.nexacorp.ai.retrieval.RetrievalService;
import com.nexacorp.ai.retrieval.model.RetrievalResult;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;
    private final RetrievalService retrievalService;

    public ChatResponse chat(ChatRequest request) {
        String userMessage = request.getMessage();
        String context = BuildContext(userMessage);

        String aiResponse = chatClient.prompt()
                .user(userMessage)
                .system(context)
                .call().content();

        return new ChatResponse(aiResponse);
    }

    private String BuildContext(String userMessage) {
        RetrievalResult retrievalResult = retrievalService.retrieve(userMessage);

        StringBuilder contextBuilder = new StringBuilder();
        for (var chunk : retrievalResult.getChunks()) {
            contextBuilder.append(chunk.getContent()).append("\n");
        }

        return contextBuilder.toString();
    }
}
