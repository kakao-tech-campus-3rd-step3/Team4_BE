package com.example.demo.mission.regular.domain;

import com.example.demo.emotion.domain.EmotionType;
import com.example.demo.mission.regular.service.score.MissionScoreMinMax;
import lombok.Getter;

@Getter
public class MissionScore {

    private final Integer sentimentScore;
    private final Integer energyScore;
    private final Integer cognitiveScore;
    private final Integer relationshipScore;
    private final Integer stressScore;
    private final Integer employmentScore;

    public MissionScore(Integer sentimentScore, Integer energyScore, Integer cognitiveScore, Integer relationshipScore, Integer stressScore, Integer employmentScore) {
        this.sentimentScore = sentimentScore;
        this.energyScore = energyScore;
        this.cognitiveScore = cognitiveScore;
        this.relationshipScore = relationshipScore;
        this.stressScore = stressScore;
        this.employmentScore = employmentScore;
    }

    public Integer calculateNormalization(Integer min, Integer max, EmotionType emotionType) {
        if (max == min) {
            return 0;
        }
        if(emotionType == EmotionType.SENTIMENT && sentimentScore !=0){
            return (int) Math.floor(((double) (sentimentScore - min) / (max - min)) * 10.0);
        }
        if(emotionType == EmotionType.ENERGY && energyScore !=0){
            return (int) Math.floor(((double) (energyScore - min) / (max - min)) * 10.0);
        }
        if(emotionType == EmotionType.COGNITIVE && cognitiveScore !=0){
            return (int) Math.floor(((double) (cognitiveScore - min) / (max - min)) * 10.0);
        }
        if(emotionType == EmotionType.RELATIONSHIP && relationshipScore !=0){
            return (int) Math.floor(((double) (relationshipScore - min) / (max - min)) * 10.0);
        }
        if(emotionType == EmotionType.STRESS && stressScore !=0){
            return (int) Math.floor(((double) (stressScore - min) / (max - min)) * 10.0);
        }
        if(emotionType == EmotionType.EMPLOYMENT && employmentScore !=0){
            return (int) Math.floor(((double) (employmentScore - min) / (max - min)) * 10.0);
        }
        return 0;
    }
}
