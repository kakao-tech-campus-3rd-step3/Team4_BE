package com.example.demo.mission.regular.domain;

import lombok.Getter;

@Getter
public class MissionTag {
    private Long id;
    private String name;

    public MissionTag(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
