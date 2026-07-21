package com.nexacorp.ai.service;

import com.nexacorp.ai.dto.ChatRequest;
import com.nexacorp.ai.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;

    public ChatResponse chat(ChatRequest request) {
        String aiResponse = chatClient.prompt()
                .user(request.getMessage())
                .call().content();

        return new ChatResponse(aiResponse);
    }
}
