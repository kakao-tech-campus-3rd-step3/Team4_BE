package com.example.demo.dto.cat;

import com.example.demo.domain.cat.Cat;
import com.example.demo.dto.item.ItemResponse;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CatResponse {

    private final String name;
    private final List<ItemResponse> equipped;

    public CatResponse(Cat cat) {
        name = cat.getName();
        equipped = cat.getEquippedItem().stream()
            .map(ownedItem -> new ItemResponse(ownedItem.getItem()))
            .toList();
    }
}
