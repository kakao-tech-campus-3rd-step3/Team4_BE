package com.example.demo.cat.service;

import com.example.demo.cat.controller.dto.CatResponse;
import com.example.demo.cat.domain.Cat;
import com.example.demo.common.exception.BusinessException;
import com.example.demo.common.exception.errorcode.CatErrorCode;
import com.example.demo.product.domain.DisplayImage;
import com.example.demo.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CatService {

    private final CatRepository catRepository;
    private final ItemQueryRepository itemQueryRepository;

    public Cat createCat(User user, String catName) {
        if (catRepository.findById(user.getId()).isPresent()) {
            throw new BusinessException(CatErrorCode.CAT_ALREADY_EXIST);
        }
        Cat cat = new Cat(user.getId(), catName);
        return catRepository.save(cat);
    }

    @Transactional(readOnly = true)
    public CatResponse getCat(User user) {
        Cat cat = getCatById(user.getId());
        List<DisplayImage> images = itemQueryRepository.findCatEquippedImages(user.getId());
        return new CatResponse(cat.getName(), images);
    }

    public CatResponse rename(User user, String catName) {
        Cat cat = getCatById(user.getId());
        cat.rename(catName);
        cat = catRepository.save(cat);
        List<DisplayImage> images = itemQueryRepository.findCatEquippedImages(user.getId());
        return new CatResponse(cat.getName(), images);
    }

    private Cat getCatById(Long id) {
        return catRepository.findById(id)
            .orElseThrow(() -> new BusinessException(CatErrorCode.CAT_NOT_FOUND));
    }


}
