package com.example.demo.external.openai;

import com.example.demo.external.openai.dto.ChatCompletionRequest;
import com.example.demo.external.openai.dto.ChatCompletionResponse;
import com.example.demo.external.openai.prompt.PromptManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OpenAiClient {

    private static final String CHAT_COMPLETION_URL = "/v1/chat/completions";
    private final RestClient restClient;

    public OpenAiClient(@Qualifier("openAiRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public String getDiaryFeedback(String diaryEntry) {
        ChatCompletionRequest request = PromptManager.diaryFeedback(diaryEntry);

        String rawResponse = restClient.post()
            .uri(CHAT_COMPLETION_URL)
            .body(request)
            .retrieve()
            .body(String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            ChatCompletionResponse response = mapper.readValue(rawResponse,
                ChatCompletionResponse.class);

            if (response == null
                || response.getChoices() == null
                || response.getChoices().isEmpty()) {
                throw new RuntimeException("OpenAI 응답이 비어있습니다.");
            }
            return response.getChoices().get(0).getMessage().getContent();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("OpenAI 응답을 파싱하지 못했습니다.", e);
        } catch (Exception e) {
            throw new RuntimeException("OpenAI 요청 처리 중 오류 발생", e);
        }
    }
}
