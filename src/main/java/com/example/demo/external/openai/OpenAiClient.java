package com.example.demo.external.openai;

import com.example.demo.external.openai.dto.CatMessage;
import com.example.demo.external.openai.dto.ChatCompletionRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OpenAiClient {

    private static final String CHAT_COMPLETION_URL = "/v1/chat/completions";
    private final RestClient restClient;
    private final ChatCompletionResponseParser parser;

    public OpenAiClient(
        @Value("${openai.clients.apikey}") String apiKey,
        @Value("${openai.clients.baseurl}") String baseUrl,
        ChatCompletionResponseParser parser
    ) {
        this.restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
        this.parser = parser;
    }

    public CatMessage getDiaryFeedback(String diaryEntry) {
        ChatCompletionRequest request = ChatCompletionRequestBuilder.buildDiaryFeedbackRequest(
            diaryEntry);

        String rawResponse = restClient.post()
            .uri(CHAT_COMPLETION_URL)
            .body(request)
            .retrieve()
            .body(String.class);

        return parser.getMessage(rawResponse);
    }
}
