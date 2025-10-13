package com.example.demo.common.infrastructure.openai.helper;

import com.example.demo.common.infrastructure.openai.OpenAiException;
import com.example.demo.common.infrastructure.openai.dto.ChatCompletionResponse;
import com.example.demo.common.infrastructure.openai.dto.OpenAiMissionScoreResponse;
import com.example.demo.common.infrastructure.openai.dto.OpenAiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpenAiResponseConverter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static OpenAiResponse convert(ChatCompletionResponse response) {
        String content = getContentFrom(response);
        return parse(content);
    }

    public static OpenAiMissionScoreResponse convertToScoreResponse(ChatCompletionResponse response) {
        String content = getContentFrom(response);
        try {
            return MAPPER.readValue(content, OpenAiMissionScoreResponse.class);
        } catch (JsonProcessingException e) {
            log.error("", e);
            return OpenAiMissionScoreResponse.zero();
        }
    }

    private static String getContentFrom(ChatCompletionResponse response) {
        if (response == null
            || response.getChoices() == null
            || response.getChoices().isEmpty()) {
            throw new OpenAiException("[OpenAi] OpenAI 응답이 비어있습니다.");
        }
        return response.getChoices().get(0).getMessage().getContent();
    }

    private static OpenAiResponse parse(String content) {
        if (content == null || content.isBlank()) {
            return new OpenAiResponse("", Map.of());
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

        return new OpenAiResponse(messagePart, codeMap);
    }
}
