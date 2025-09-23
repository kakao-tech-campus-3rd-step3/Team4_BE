package com.example.demo.common.infrastructure.openai;

import com.example.demo.common.infrastructure.openai.dto.ChatCompletionRequest;
import com.example.demo.common.infrastructure.openai.dto.ChatCompletionResponse;
import com.example.demo.common.infrastructure.openai.dto.OpenAiResponse;
import com.example.demo.common.infrastructure.openai.helper.ChatCompletionRequestFactory;
import com.example.demo.common.infrastructure.openai.helper.OpenAiResponseConverter;
import java.util.List;
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

        try {
            ChatCompletionResponse response = restClient.post()
                .uri(CHAT_COMPLETION_URL)
                .body(request)
                .retrieve()
                .body(ChatCompletionResponse.class);
            return OpenAiResponseConverter.convert(response);
        } catch (HttpServerErrorException e) {
            // 5xx -> 외부 서버 문제
            throw new RuntimeException("");
        } catch (HttpClientErrorException | ResourceAccessException e) {
            // 4xx, 네트워크 실패 -> 내 요청이 잘못됨, 내 서버 상태 이상
            throw new RuntimeException("");
        } catch (RestClientException e) {
            // json 역직렬화
            throw new RuntimeException("");
        }
    }

    /**
     *
     * @param chatEntry
     * @param context
     * @return
     */
    public OpenAiResponse getChatResponse(String chatEntry, List<String> context) {
        ChatCompletionRequest request = requestFactory.buildMyCatChatRequest(chatEntry, context);

        try {
            ChatCompletionResponse response = restClient.post()
                .uri(CHAT_COMPLETION_URL)
                .body(request)
                .retrieve()
                .body(ChatCompletionResponse.class);
            return OpenAiResponseConverter.convert(response);
        } catch (HttpServerErrorException e) {
            // 5xx -> 외부 서버 문제
            throw new RuntimeException("");
        } catch (HttpClientErrorException | ResourceAccessException e) {
            // 4xx, 네트워크 실패 -> 내 요청이 잘못됨, 내 서버 상태 이상
            throw new RuntimeException("");
        } catch (RestClientException e) {
            // json 역직렬화
            throw new RuntimeException("");
        }
    }
}
