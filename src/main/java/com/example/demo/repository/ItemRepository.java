package com.example.demo.repository;

import com.example.demo.domain.cat.Item;
import com.example.demo.domain.cat.ItemCategoryEnum;
import com.example.demo.dto.item.ShopItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("""
        select new com.example.demo.dto.item.ShopItemResponse(
            i,
            case when oi.id is not null then true else false end
        )
        from Item i
        left join OwnedItem oi on oi.item.id = i.id and oi.cat.id = :catId
        where i.category = :category
        order by i.id
        """)
    Page<ShopItemResponse> findAllByCategoryOrderByIdAsc(Pageable pageable,
        ItemCategoryEnum category, Long catId);

}
