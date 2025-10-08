package com.example.demo.mission.regular.domain.score;

import com.example.demo.emotion.domain.EmotionType;

public class RelationshipScore extends MissionScore {

    public RelationshipScore(Integer score) {
        super(score);
    }

    @Override
    public EmotionType getEmotionType() {
        return EmotionType.RELATIONSHIP;
    }
}
