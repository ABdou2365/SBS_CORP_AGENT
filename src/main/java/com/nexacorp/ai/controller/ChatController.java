package com.nexacorp.ai.controller;

import com.nexacorp.ai.dto.ChatRequest;
import com.nexacorp.ai.dto.ChatResponse;
import com.nexacorp.ai.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
class ChatController {

    private final ChatService chatService;

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        return chatService.chat(request);
    }

}
