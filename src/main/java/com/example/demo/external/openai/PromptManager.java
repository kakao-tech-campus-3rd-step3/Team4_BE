package com.example.demo.external.openai;

import com.example.demo.external.openai.dto.ChatCompletionRequest;
import com.example.demo.external.openai.dto.ChatCompletionRequest.Message;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.io.ClassPathResource;

public class PromptManager {

    private static final String DIARY_FEEDBACK_SYSTEM_PROMPT = "diary_feedback.prompt";
    private static final String DIARY_FEEDBACK_EXAMPLE_PROMPT = "diary_feedback_example.prompt";

    public static final List<Message> DIARY_FEEDBACK_BASE_MESSAGE = buildBasePrompt(
        DIARY_FEEDBACK_SYSTEM_PROMPT, DIARY_FEEDBACK_EXAMPLE_PROMPT);

    private static List<ChatCompletionRequest.Message> buildBasePrompt(String systemPromptFileName,
        String examplePromptFileName) {
        List<ChatCompletionRequest.Message> message = new ArrayList<>();
        try {
            ClassPathResource systemPath = new ClassPathResource("prompts/" + systemPromptFileName);
            String system = Files.readString(systemPath.getFile().toPath());
            message.add(systemMessage(system));

            ClassPathResource examplePath = new ClassPathResource(
                "prompts/" + examplePromptFileName);
            List<String> examples = Files.readString(examplePath.getFile().toPath()).lines()
                .toList();
            boolean isUser = true;
            for (String example : examples) {
                if (example.isBlank()) {
                    continue;
                } else if (isUser) {
                    message.add(userMessage(example));
                } else {
                    message.add(assistantMessage(example));
                }
                isUser = !isUser;
            }

            return message;
        } catch (IOException e) {
            throw new RuntimeException("프롬프트 파일 로딩에 실패했습니다.", e);
        }
    }

    private static ChatCompletionRequest.Message systemMessage(String content) {
        return ChatCompletionRequest.Message.builder()
            .role("system")
            .content(content)
            .build();
    }

    private static ChatCompletionRequest.Message userMessage(String content) {
        return ChatCompletionRequest.Message.builder()
            .role("user")
            .content(content)
            .build();
    }

    private static ChatCompletionRequest.Message assistantMessage(String content) {
        return ChatCompletionRequest.Message.builder()
            .role("assistant")
            .content(content)
            .build();
    }
}
