package com.example.demo.domain.category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categoryDetails")
public class CategoryDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detailCategoryId")
    private Long id;

    @Column(nullable = false)
    private String detailCategoryName;

    @OneToMany(mappedBy = "categoryDetail")
    private List<MissionCategoryDetail> missionCategoryDetails = new ArrayList<>();

    protected CategoryDetail() {
    }

}
