package com.example.demo.chat.infrastructure.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "chat_memory")
@Getter
public class ChatMemory {

    private static final String SYSTEM_TAG = "[FROM SYSTEM] ";
    private static final String HIGH_DANGER_MESSAGE = "사용자가 극심한 우울증세를 보이고 있습니다. 사용자를 진정시키고 전문적인 도움을 받을 수 있도록 유도하십시오.";
    private static final String BURST_DANGER_MESSAGE = "사용자가 최근 우울증이 심해지고있습니다. 상담사처럼 사용자의 아픔에 공감해주고 위로 격려하며 사용자의 부정적인 사고를 천천히 고쳐가도록 지도해주십시오.";
    private static final String CHRONIC_DANGER_MESSAGE = "";

    @Id
    private Long userId;

    @Setter
    @Column(nullable = false, columnDefinition = "TEXT")
    private String memory;

    protected ChatMemory() {}

    public ChatMemory(Long userId) {
        this.userId = userId;
        memory = "";
    }

    public void appendHighDangerMessage() {
        clearDangerMessage();
        memory += "\n" + SYSTEM_TAG + HIGH_DANGER_MESSAGE;
    }

    public void appendBurstDangerMessage() {
        clearDangerMessage();
        memory += "\n" + SYSTEM_TAG + BURST_DANGER_MESSAGE;
    }

    public void appendChronicDangerMessage() {
        clearDangerMessage();
        memory += "\n" + SYSTEM_TAG + CHRONIC_DANGER_MESSAGE;
    }

    public void clearDangerMessage() {
        memory = memory.lines()
            .filter(line -> !line.contains(SYSTEM_TAG))
            .collect(Collectors.joining("\n"));
    }
}
