package com.example.demo.external.openai.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CatMessage {

    private String message;
    private Map<String, String> codeBlock;

    public String toString() {
        StringBuilder sb = new StringBuilder(message);
        sb.append("\n```\n");
        for (String header : codeBlock.keySet()) {
            sb.append(header)
                .append(':')
                .append(codeBlock.get(header))
                .append('\n');
        }
        sb.append("```");
        return sb.toString();
    }
}
