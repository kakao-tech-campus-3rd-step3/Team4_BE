package com.example.demo.common.infrastructure.openai.helper;

import com.example.demo.common.infrastructure.openai.dto.ChatCompletionResponse;
import com.example.demo.common.infrastructure.openai.dto.OpenAiResponse;
import java.util.HashMap;
import java.util.Map;

public class OpenAiResponseConverter {

    public static OpenAiResponse convert(ChatCompletionResponse response) {
        if (response == null
            || response.getChoices() == null
            || response.getChoices().isEmpty()) {
            throw new RuntimeException("OpenAI 응답이 비어있습니다.");
        }
        String content = response.getChoices().get(0).getMessage().getContent();

        return parse(content);
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
