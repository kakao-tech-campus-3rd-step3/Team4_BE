package com.example.demo.service;

import com.example.demo.domain.cat.Cat;
import com.example.demo.domain.user.User;
import com.example.demo.dto.cat.CatResponse;
import com.example.demo.repository.CatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CatService {

    private final CatRepository catRepository;

    public CatResponse create(User user, String catName) {
        if (catRepository.findByIdJoinFetchOwnedItems(user.getId()).isPresent()) {
            throw new RuntimeException("이미 고양이가 생성되어있습니다.");
        }
        Cat created = new Cat(user, catName);
        Cat saved = catRepository.save(created);
        return new CatResponse(saved);
    }

    @Transactional(readOnly = true)
    public CatResponse get(User user) {
        Cat found = findCat(user);
        return new CatResponse(found);
    }

    public CatResponse rename(User user, String catName) {
        Cat found = findCat(user);
        found.rename(catName);
        return new CatResponse(found);
    }

    private Cat findCat(User user) {
        return catRepository.findByIdJoinFetchOwnedItems(user.getId())
            .orElseThrow(() -> new RuntimeException("고양이를 찾을 수 없습니다."));
    }
}
