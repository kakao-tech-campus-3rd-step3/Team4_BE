package com.example.demo.chat.service;

import com.example.demo.chat.controller.dto.ChatHistoryResponse;
import com.example.demo.chat.controller.dto.ChatListResponse;
import com.example.demo.chat.controller.dto.ChatRequest;
import com.example.demo.chat.controller.dto.ChatResponse;
import com.example.demo.chat.domain.Chat;
import com.example.demo.chat.infrastructure.jpa.Sender;
import com.example.demo.common.exception.BusinessException;
import com.example.demo.common.exception.errorcode.OpenAiErrorCode;
import com.example.demo.common.infrastructure.openai.OpenAiClient;
import com.example.demo.common.infrastructure.openai.dto.OpenAiResponse;
import com.example.demo.user.domain.User;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final OpenAiClient openAiClient;

    public ChatResponse chat(ChatRequest request, User user) {
        String message = request.getMessage();
        OpenAiResponse catResponse = getAiResponse(message, user);
        Integer dangerScore = getDangerScore(catResponse);

        Chat userChat = new Chat(user.getId(), message, Sender.USER);
        Chat catChat = new Chat(user.getId(), catResponse.getMessage(), Sender.CAT, dangerScore);
        chatRepository.save(userChat);
        chatRepository.save(catChat);

        return new ChatResponse(catResponse.getMessage());
    }

    private OpenAiResponse getAiResponse(String message, User user) {
        List<Chat> pastMessages = chatRepository.findTop10ByUserIdOrderByCreatedAtDesc(
                user.getId());
        List<String> contextStrings = pastMessages.stream()
                .map(chat -> chat.getSender() + ": " + chat.getContent()).toList();

        return openAiClient.getChatResponse(message, contextStrings);
    }

    private Integer getDangerScore(OpenAiResponse response) {
        Map<String, String> codeBlock = response.getCodeBlock();
        if (codeBlock == null || !codeBlock.containsKey("emotion-score")) {
            return null;
        }
        String scoreString = codeBlock.get("emotion-score");
        try {
            return Integer.parseInt(scoreString.trim());
        } catch (NumberFormatException e) {
            throw new BusinessException(OpenAiErrorCode.OPEN_AI_DATA_PARSE_ERROR);
        }
    }

    public ChatListResponse getChatHistory(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Chat> chatPage = chatRepository.findByUserId(userId, pageable);

        List<ChatHistoryResponse> chatResponses = chatPage.getContent().stream()
                .map(chat -> new ChatHistoryResponse(chat.getContent(), chat.getSender(),
                        chat.getCreatedAt()))
                .toList();

        return new ChatListResponse(
                chatResponses,
                chatPage.getTotalPages(),
                chatPage.getTotalElements()
        );
    }
}
