package com.example.demo.emotion.domain;

import com.example.demo.chat.infrastructure.jpa.LongTermMemory;

public enum DangerState {
    HIGH_DANGER {
        @Override
        public void adjust(LongTermMemory memory) {
            memory.appendHighDangerMessage();
        }
    },
    BURST {
        @Override
        public void adjust(LongTermMemory memory) {
            memory.appendBurstDangerMessage();
        }
    },
    CHRONIC {
        @Override
        public void adjust(LongTermMemory memory) {
            memory.appendChronicDangerMessage();
        }
    },
    STABLE {
        @Override
        public void adjust(LongTermMemory memory) {
            memory.clearDangerMessage();
        }
    };

    public abstract void adjust(LongTermMemory memory);
}