package com.example.demo.chat.infrastructure.jpa;

import com.example.demo.chat.domain.Chat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sender sender;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private Integer dangerScore;

    protected Message() {
    }

    private Message(
            Long id,
            Long userId,
            Sender sender,
            String content,
            LocalDateTime createdAt,
            Integer dangerScore
    ) {
        this.id = id;
        this.userId = userId;
        this.sender = sender;
        this.content = content;
        this.createdAt = createdAt;
        this.dangerScore = dangerScore;
    }

    public Chat toModel() {
        return new Chat(
                id,
                userId,
                sender,
                content,
                createdAt,
                dangerScore
        );
    }

    public static Message fromModel(Chat chat) {
        return new Message(
                chat.getId(),
                chat.getUserId(),
                chat.getSender(),
                chat.getContent(),
                chat.getCreatedAt(),
                chat.getDangerScore()
        );
    }
}
