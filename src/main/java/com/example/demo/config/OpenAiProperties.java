package com.example.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class OpenAiProperties {

    private String apiKey;
    private String baseUrl;

    public OpenAiProperties(
        @Value("${openai.clients.apikey}") String apiKey,
        @Value("${openai.clients.baseurl}") String baseUrl
    ) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }
}
