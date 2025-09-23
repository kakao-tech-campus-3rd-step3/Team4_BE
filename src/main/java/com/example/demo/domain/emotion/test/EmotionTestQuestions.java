package com.example.demo.domain.emotion.test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.core.io.ClassPathResource;

@Getter
public class EmotionTestQuestions {

    private List<EmotionTestQuestion> questions = new ArrayList<>();

    public EmotionTestQuestions() {
        load();
        System.out.println(questions);
    }

    private void load() {
        try {
            ClassPathResource emotionTestPath = new ClassPathResource("data/emotion_test.txt");
            StringTokenizer st = new StringTokenizer(Files.readString(emotionTestPath.getFile().toPath()), "\n");

            for (int i=0; i<6; i++) {
                String question = st.nextToken().trim();
                List<String> answers = new ArrayList<>();
                for (int j=0; j<4; j++) {
                    answers.add(st.nextToken().trim());
                }
                String imageUrl = st.nextToken().trim();
                questions.add(new EmotionTestQuestion((long) i+1, question, answers, imageUrl));
            }
        } catch (IOException e) {
            throw new RuntimeException("파일을 찾을 수 없습니다.", e);
        }
    }

    @Getter
    @AllArgsConstructor
    @ToString
    public class EmotionTestQuestion {
        private Long id;
        private String question;
        private List<String> answers = new ArrayList<>();
        private String imageUrl;
    }
}