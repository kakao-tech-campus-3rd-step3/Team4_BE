package com.example.demo.domain.emotion.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        ClassPathResource emotionTestPath = new ClassPathResource("data/emotion_test.txt");

        try (InputStream is = emotionTestPath.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            List<String> lines = reader.lines().toList();

            StringTokenizer st = new StringTokenizer(String.join("\n", lines), "\n");

            for (int i = 0; i < 6; i++) {
                String question = st.nextToken().trim();
                List<String> answers = new ArrayList<>();
                for (int j = 0; j < 4; j++) {
                    answers.add(st.nextToken().trim());
                }
                String imageUrl = st.nextToken().trim();
                questions.add(new EmotionTestQuestion((long) i + 1, question, answers, imageUrl));
            }

        } catch (IOException e) {
            throw new RuntimeException("리소스를 읽는 중 오류 발생", e);
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