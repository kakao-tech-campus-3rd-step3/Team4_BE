package com.example.demo.cat.infrastructure.jpa;

import com.example.demo.cat.controller.dto.ItemResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemQueryJpaRepository extends JpaRepository<ItemEntity, Long> {

    @Query("""
        select new com.example.demo.cat.controller.dto.ItemResponse(
            i.id,
            p.category,
            p.name,
            p.displayImage.imageUrl,
            p.displayImage.offsetX,
            p.displayImage.offsetY,
            i.equipped
        )
        from CatEntity c
        join c.itemEntities i
        join ProductItemEntity p on i.productId = p.id
        where c.userEntity.id = :userId
        """)
    List<ItemResponse> findItemResponseByUserId(@Param("userId") Long userId);
}
