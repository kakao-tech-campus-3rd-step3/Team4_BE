package com.example.demo.external.openai;

import com.example.demo.external.openai.dto.CatMessage;
import com.example.demo.external.openai.dto.ChatCompletionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatCompletionResponseParser {

    private final ObjectMapper mapper;

    public CatMessage getMessage(String rawResponse) {
        try {
            ChatCompletionResponse response = mapper.readValue(rawResponse,
                ChatCompletionResponse.class);

            if (response == null
                || response.getChoices() == null
                || response.getChoices().isEmpty()) {
                throw new RuntimeException("OpenAI 응답이 비어있습니다.");
            }
            String content = response.getChoices().get(0).getMessage().getContent();
            return parse(content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("OpenAI 응답을 파싱하지 못했습니다.", e);
        } catch (Exception e) {
            throw new RuntimeException("OpenAI 요청 처리 중 오류 발생", e);
        }
    }

    private CatMessage parse(String content) {
        if (content == null || content.isBlank()) {
            return new CatMessage("", Map.of());
        }

        String messagePart = content;
        Map<String, String> codeMap = new HashMap<>();
        int start = content.indexOf("```");
        int end = content.lastIndexOf("```");

        if (start != -1 && end != -1 && start != end) {
            messagePart = content.substring(0, start).trim();
            String codeBlockContent = content.substring(start + 3, end).trim();

            String[] entries = codeBlockContent.split("[,\\n]+");
            for (String entry : entries) {
                entry = entry.trim();
                if (entry.contains(":")) {
                    String[] kv = entry.split(":", 2);
                    if (kv.length == 2) {
                        codeMap.put(kv[0].trim(), kv[1].trim());
                    }
                }
            }
        }

        return new CatMessage(messagePart, codeMap);
    }
}
