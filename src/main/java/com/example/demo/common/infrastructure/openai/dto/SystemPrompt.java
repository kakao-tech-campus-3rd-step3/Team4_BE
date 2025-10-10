package com.example.demo.common.infrastructure.openai.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemPrompt {

    private String system;

    protected  SystemPrompt() {}

    public SystemPrompt(String prompt) {
        system = prompt;
    }

    public void appendLongTermMemory(String memory) {
        system += "# Long Term Memory\n" + memory;
    }
}
