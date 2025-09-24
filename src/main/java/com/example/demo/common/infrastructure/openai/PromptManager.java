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

    private List<Message> diaryFeedbackBaseMessages;
    private List<Message> myCatChatBaseMessages;

    public PromptManager() {
        SystemPrompt system = YamlResourceLoader.load(DIARY_FEEDBACK_SYSTEM_PATH, SystemPrompt.class);
        List<ExamplePrompt> examples = YamlResourceLoader.loadList(DIARY_FEEDBACK_EXAMPLE_PATH, new TypeReference<List<ExamplePrompt>>() {});
        diaryFeedbackBaseMessages = buildBaseMessages(system, examples);

        system = YamlResourceLoader.load(MY_CAT_CHAT_SYSTEM_PATH, SystemPrompt.class);
        myCatChatBaseMessages = buildBaseMessages(system, new ArrayList<>());
    }

    public List<Message> getDiaryFeedbackBaseMessages() {
        return new ArrayList<>(diaryFeedbackBaseMessages);
    }

    public List<Message> getMyCatChatBaseMessages() {
        return new ArrayList<>(myCatChatBaseMessages);
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
