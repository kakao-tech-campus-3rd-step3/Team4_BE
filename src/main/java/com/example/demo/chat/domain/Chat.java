package com.example.demo.chat.domain;

import com.example.demo.chat.infrastructure.jpa.Sender;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Chat {

    private Long id;
    private Long userId;
    private Sender sender;
    private String content;
    private LocalDateTime createdAt;
    private Integer dangerScore;

    public Chat(Long id, Long userId, Sender sender, String content, LocalDateTime createdAt,
            Integer dangerScore) {
        this.id = id;
        this.userId = userId;
        this.sender = sender;
        this.content = content;
        this.createdAt = createdAt;
        this.dangerScore = dangerScore;
    }

    public Chat(Long userId, String content, Sender sender) {
        this(null, userId, sender, content, LocalDateTime.now(), null);
    }

    public Chat(Long userId, String content, Sender sender, Integer dangerScore) {
        this(null, userId, sender, content, LocalDateTime.now(), dangerScore);
    }

}
