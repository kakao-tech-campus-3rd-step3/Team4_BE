package com.example.demo.domain.chat;

import com.example.demo.domain.common.BaseEntity;
import com.example.demo.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "chat")
public class Chat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String response;

    @Column
    private Integer dangerScore;

    protected Chat() {
    }

    private Chat(User user, String content) {
        this.user = user;
        this.content = content;
    }

    public static Chat of(User user, String content) {
        return new Chat(user, content);
    }

    public void updateResponse(String response) {
        this.response = response;
    }

    public void updateDangerScore(int dangerScore) {
        this.dangerScore = dangerScore;
    }
}
