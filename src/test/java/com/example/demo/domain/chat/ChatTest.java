package com.example.demo.domain.chat;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.chat.domain.Chat;
import com.example.demo.chat.infrastructure.jpa.Sender;
import org.junit.jupiter.api.Test;

public class ChatTest {

    @Test
    void 메시지_생성_성공() {
        // given
        Long userId = 10L;
        String message = "hi";

        // when
        Chat userChat = new Chat(userId, message, Sender.USER);

        // then
        assertThat(userChat.getUserId()).isEqualTo(10L);
        assertThat(userChat.getContent()).isEqualTo("hi");
        assertThat(userChat.getSender()).isEqualTo(Sender.USER);
        assertThat(userChat.getDangerScore()).isNull();
        assertThat(userChat.getId()).isNull();
        assertThat(userChat.getCreatedAt()).isNotNull();
    }

    @Test
    void cat_메시지_생성_및_위험점수_저장() {
        // given
        Long userId = 20L;
        String aiResponse = "반갑다냥";
        int dangerScore = -5;

        // when
        Chat aiChat = new Chat(userId, aiResponse, Sender.CAT, dangerScore);

        // then
        assertThat(aiChat.getUserId()).isEqualTo(20L);
        assertThat(aiChat.getContent()).isEqualTo("반갑다냥");
        assertThat(aiChat.getSender()).isEqualTo(Sender.CAT);
        assertThat(aiChat.getDangerScore()).isEqualTo(-5);
        assertThat(aiChat.getId()).isNull();
    }

    @Test
    void 위험점수_null() {
        // given
        Long userId = 30L;
        String aiResponse = "냥";
        Integer nullDangerScore = null;

        // when
        Chat aiChat = new Chat(userId, aiResponse, Sender.CAT, nullDangerScore);

        // then
        assertThat(aiChat.getDangerScore()).isNull();
    }
}
