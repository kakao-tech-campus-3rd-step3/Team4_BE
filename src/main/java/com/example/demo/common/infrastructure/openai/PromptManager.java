package com.example.demo.common.infrastructure.openai;

import com.example.demo.common.infrastructure.openai.dto.ChatCompletionRequest.Message;
import com.example.demo.common.infrastructure.openai.dto.ExamplePrompt;
import com.example.demo.common.infrastructure.openai.dto.SystemPrompt;
import com.example.demo.common.util.YamlResourceLoader;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PromptManager {

    private static final String DIARY_FEEDBACK_SYSTEM_PATH = "prompts/diary_feedback_system.yml";
    private static final String DIARY_FEEDBACK_EXAMPLE_PATH = "prompts/diary_feedback_example.yml";
    private static final String MY_CAT_CHAT_SYSTEM_PATH = "prompts/my_cat_chat_system.yml";
    private static final String CUSTOM_MISSION_EVALUATION_SYSTEM_PATH = "prompts/custom_mission_evaluation_system.yml";

    private final List<Message> diaryFeedbackBaseMessages;
    private final SystemPrompt myCatChatSystemPrompt;
    private final SystemPrompt customMissionEvaluationPrompt;

    public PromptManager() {
        SystemPrompt system = YamlResourceLoader.load(DIARY_FEEDBACK_SYSTEM_PATH, SystemPrompt.class);
        List<ExamplePrompt> examples = YamlResourceLoader.loadList(DIARY_FEEDBACK_EXAMPLE_PATH, new TypeReference<List<ExamplePrompt>>() {});
        diaryFeedbackBaseMessages = buildBaseMessages(system, examples);

        myCatChatSystemPrompt = YamlResourceLoader.load(MY_CAT_CHAT_SYSTEM_PATH, SystemPrompt.class);
        customMissionEvaluationPrompt = YamlResourceLoader.load(
            CUSTOM_MISSION_EVALUATION_SYSTEM_PATH, SystemPrompt.class);
    }

    public List<Message> getDiaryFeedbackBaseMessages() {
        return new ArrayList<>(diaryFeedbackBaseMessages);
    }

    public List<Message> getMyCatChatBaseMessages(String memory) {
        SystemPrompt copy = new SystemPrompt(myCatChatSystemPrompt.getSystem());
        copy.appendLongTermMemory(memory);
        return buildBaseMessages(copy, new ArrayList<>());
    }

    public List<Message> getCustomMissionEvaluateBaseMessages() {
        return buildBaseMessages(customMissionEvaluationPrompt, new ArrayList<>());
    }

    private List<Message> buildBaseMessages(SystemPrompt system, List<ExamplePrompt> examples) {
        List<Message> baseMessages = new ArrayList<>();
        baseMessages.add(
            Message.builder()
                .role("system")
                .content(system.getSystem())
                .build()
        );
        examples.forEach(e -> {
            baseMessages.add(
                Message.builder()
                    .role("user")
                    .content(e.getUser())
                    .build()
            );
            baseMessages.add(
                Message.builder()
                    .role("assistant")
                    .content(e.getUser())
                    .build()
            );
        });
        return baseMessages;
    }
}
