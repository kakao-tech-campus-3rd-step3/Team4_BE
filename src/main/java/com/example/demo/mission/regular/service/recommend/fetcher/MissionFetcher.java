package com.example.demo.mission.regular.service.recommend.fetcher;

import com.example.demo.emotion.domain.EmotionType;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.regular.domain.RegularMission;
import java.util.List;

public interface MissionFetcher {

    EmotionType getEmotionType();

    List<RegularMission> fetch(MissionCategoryEnum category);
}
