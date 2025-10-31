package com.example.demo.emotion.domain;

import com.example.demo.chat.infrastructure.jpa.ChatMemory;

public enum DangerState {
    HIGH_DANGER {
        @Override
        public void adjust(ChatMemory memory) {
            memory.appendHighDangerMessage();
        }
    },
    BURST {
        @Override
        public void adjust(ChatMemory memory) {
            memory.appendBurstDangerMessage();
        }
    },
    CHRONIC {
        @Override
        public void adjust(ChatMemory memory) {
            memory.appendChronicDangerMessage();
        }
    },
    STABLE {
        @Override
        public void adjust(ChatMemory memory) {
            memory.clearDangerMessage();
        }
    };

    public abstract void adjust(ChatMemory memory);
}