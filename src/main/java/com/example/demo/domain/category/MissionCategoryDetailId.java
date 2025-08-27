package com.example.demo.domain.category;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MissionCategoryDetailId implements Serializable {

    private Long missionId;
    private Long detailCategoryId;

    protected MissionCategoryDetailId() {
    }

    public MissionCategoryDetailId(Long missionId, Long detailCategoryId) {
        this.missionId = missionId;
        this.detailCategoryId = detailCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MissionCategoryDetailId that = (MissionCategoryDetailId) o;

        return Objects.equals(missionId, that.missionId) && Objects.equals(detailCategoryId,
            that.detailCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(missionId, detailCategoryId);
    }

}
