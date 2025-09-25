package com.example.demo.emotiontest.infrastructure.dto;

import com.example.demo.emotiontest.domain.EmotionTest;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmotionTestDto {

    private Long id;
    private String question;
    private List<String> answers;
    private String imageUrl;

    protected EmotionTestDto() {}

    public EmotionTest toModel() {
        return new EmotionTest(id, question, answers, imageUrl);
    }
}
