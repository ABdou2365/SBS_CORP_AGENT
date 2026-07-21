package com.nexacorp.ai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/")
class HealthController {

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
