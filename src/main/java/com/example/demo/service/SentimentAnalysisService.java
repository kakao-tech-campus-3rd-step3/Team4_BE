package com.example.demo.service;

import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public class SentimentAnalysisService {

    public Sentiment analyzeSentiment(String text) throws IOException {
        try (LanguageServiceClient language = LanguageServiceClient.create()) {
            Document doc = Document.newBuilder().setContent(text).setType(Document.Type.PLAIN_TEXT)
                .build();
            return language.analyzeSentiment(doc).getDocumentSentiment();
        }
    }
}