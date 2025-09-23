package com.example.demo.product.infrastructure.jpa;

import com.example.demo.product.domain.DisplayImage;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class DisplayImageEmbeddable {

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Float offsetX;

    @Column(nullable = false)
    private Float offsetY;

    public DisplayImage toModel() {
        return new DisplayImage(imageUrl, offsetX, offsetY);
    }
}
