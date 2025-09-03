package com.example.demo.repository;

import com.example.demo.domain.cat.Item;
import com.example.demo.domain.cat.ItemCategoryEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findAllByCategory(Pageable pageable, ItemCategoryEnum category);
}
