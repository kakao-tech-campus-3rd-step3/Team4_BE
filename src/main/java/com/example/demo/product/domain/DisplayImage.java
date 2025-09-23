package com.example.demo.product.domain;

import lombok.Getter;

@Getter
public class DisplayImage {

    private String imageUrl;
    private Float offsetX;
    private Float offsetY;

    public DisplayImage(String imageUrl, Float offsetX, Float offsetY) {
        this.imageUrl = imageUrl;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
}
