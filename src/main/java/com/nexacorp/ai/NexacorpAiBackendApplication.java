package com.nexacorp.ai;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NexacorpAiBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexacorpAiBackendApplication.class, args);
    }

    @PostConstruct
    public void checkGeminiKey() {
        String key = System.getenv("GEMINI_API_KEY");

        System.out.println("GEMINI_API_KEY exists: " + (key != null));
        System.out.println("GEMINI_API_KEY length: " + (key != null ? key.length() : 0));
    }

}
