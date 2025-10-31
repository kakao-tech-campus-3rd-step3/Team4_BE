package com.example.demo.chat.event;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatMemoryUpdateEvent {
    private final Long userId;
    private final List<String> context;
}
