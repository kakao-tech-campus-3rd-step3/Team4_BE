package com.example.demo.cat.service;

import com.example.demo.cat.controller.dto.ItemResponse;
import com.example.demo.product.domain.DisplayImage;
import java.util.List;

public interface ItemQueryRepository {

    List<ItemResponse> findAllByUserId(Long id);

    List<DisplayImage> findCatEquippedImages(Long id);

}
