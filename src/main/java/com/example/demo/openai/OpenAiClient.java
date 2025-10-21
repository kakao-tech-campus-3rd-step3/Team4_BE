package com.example.demo.openai;

import com.example.demo.exception.service.OpenAiException;
import com.example.demo.openai.dto.ChatCompletionRequest;
import com.example.demo.openai.dto.ChatCompletionResponse;
import com.example.demo.openai.dto.OpenAiMissionScoreResponse;
import com.example.demo.openai.dto.OpenAiResponse;
import com.example.demo.openai.helper.ChatCompletionRequestFactory;
import com.example.demo.openai.helper.OpenAiResponseConverter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@Slf4j
public class OpenAiClient {

    private static final String CHAT_COMPLETION_URL = "/v1/chat/completions";
    private final RestClient restClient;
    private final ChatCompletionRequestFactory requestFactory;

    public OpenAiClient(
        @Value("${openai.clients.apikey}") String apiKey,
        @Value("${openai.clients.baseurl}") String baseUrl,
        ChatCompletionRequestFactory requestFactory
    ) {
        this.restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
        this.requestFactory = requestFactory;
    }

    public OpenAiResponse getFeedback(String diaryEntry) {
        ChatCompletionRequest request = requestFactory.buildDiaryFeedbackRequest(diaryEntry);
        ChatCompletionResponse response = sendRequest(request);
        return OpenAiResponseConverter.convert(response);
    }

    public OpenAiResponse getChatResponse(String chatEntry, List<String> context, String memory) {
        ChatCompletionRequest request = requestFactory.buildMyCatChatRequest(chatEntry, context, memory);
        ChatCompletionResponse response = sendRequest(request);
        return OpenAiResponseConverter.convert(response);
    }

    public OpenAiMissionScoreResponse getScore(String content) {
        ChatCompletionRequest request = requestFactory.buildCustomMissionEvaluateRequest(
            content);
        ChatCompletionResponse response = sendRequest(request);
        return OpenAiResponseConverter.convertToScoreResponse(response);
    }

    private ChatCompletionResponse sendRequest(ChatCompletionRequest request) {
        try {
            return restClient.post()
                .uri(CHAT_COMPLETION_URL)
                .body(request)
                .retrieve()
                .body(ChatCompletionResponse.class);
        } catch (HttpServerErrorException | ResourceAccessException e) {
            throw new OpenAiException(e);
        } catch (HttpClientErrorException e) {
            throw new OpenAiException("ChatCompletionRequest가 잘못되었습니다: " + request, e);
        } catch (RestClientException e) {
            throw new OpenAiException("OpenAi 응답 역직렬화에 실패했습니다.", e);
        }
    }
}
