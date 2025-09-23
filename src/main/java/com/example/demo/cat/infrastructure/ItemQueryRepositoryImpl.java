package com.example.demo.cat.infrastructure;

import com.example.demo.cat.controller.dto.ItemResponse;
import com.example.demo.cat.infrastructure.jpa.ItemQueryJpaRepository;
import com.example.demo.cat.service.ItemQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemQueryRepositoryImpl implements ItemQueryRepository {

    private final ItemQueryJpaRepository itemRepository;

    @Override
    public List<ItemResponse> findAllByUserId(Long userId) {
        return itemRepository.findItemResponseByUserId(userId);
    }
}
