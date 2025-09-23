package com.example.demo.cat.infrastructure;

import com.example.demo.cat.controller.dto.ItemResponse;
import com.example.demo.cat.controller.dto.ItemView;
import com.example.demo.cat.infrastructure.jpa.ItemQueryJpaRepository;
import com.example.demo.cat.service.ItemQueryRepository;
import com.example.demo.product.domain.DisplayImage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemQueryRepositoryImpl implements ItemQueryRepository {

    private final ItemQueryJpaRepository itemRepository;

    @Override
    public List<ItemResponse> findAllByUserId(Long userId) {
        List<ItemView> view = findItemViewByUserId(userId);
        return view.stream()
            .map(v -> new ItemResponse(v.getId(), v.getSlot(), v.getName(), v.getImage()
                .getImageUrl(), v.getImage().getOffsetX(), v.getImage().getOffsetY(),
                v.getIsUsed())).toList();
    }

    @Override
    public List<DisplayImage> findCatEquippedImages(Long id) {
        List<ItemView> view = findItemViewByUserId(id);
        return view.stream().filter(ItemView::getIsUsed).map(ItemView::getImage).toList();
    }

    private List<ItemView> findItemViewByUserId(Long userId) {
        return itemRepository.findItemViewByUserId(userId);
    }
}
