package com.example.demo.cat.infrastructure.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class DisplayImage {

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Float offsetX;

    @Column(nullable = false)
    private Float offsetY;
}
