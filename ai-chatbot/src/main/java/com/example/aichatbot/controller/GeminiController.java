package com.example.aichatbot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
public class GeminiController {

    @Value("${gemini.api.key}")
    private String apiKey;

    @GetMapping("/apikey")
    public String getApiKey() {
        return apiKey;
    }
}
