package com.example.demo.mission.regular.service.recommend.fetcher;

import com.example.demo.emotion.domain.EmotionType;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MissionFetcherSelector {

    private final Map<EmotionType, MissionFetcher> fetchers;

    public MissionFetcherSelector(List<MissionFetcher> fetcherList) {
        this.fetchers = fetcherList.stream().collect(
            Collectors.toMap(
                MissionFetcher::getEmotionType,
                Function.identity()
            ));
    }

    public MissionFetcher getFetcher(EmotionType emotionType) {
        MissionFetcher fetcher = fetchers.get(emotionType);
        if (fetcher == null) {
            throw new RuntimeException("Fetcher Not Found");
        }
        return fetcher;
    }
}