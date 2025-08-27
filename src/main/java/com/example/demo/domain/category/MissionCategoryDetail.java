package com.example.demo.domain.category;

import com.example.demo.domain.mission.Mission;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "mission_category_details")
public class MissionCategoryDetail {

    @EmbeddedId
    private MissionCategoryDetailId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("missionId")
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("detailCategoryId")
    @JoinColumn(name = "detail_category_id")
    private CategoryDetail categoryDetail;

    protected MissionCategoryDetail() {
    }

}
