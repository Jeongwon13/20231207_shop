package com.hy.shop.main.controller;

import com.hy.shop.item.model.vo.ItemType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class CommonControllerAdvice {

    @ModelAttribute("itemTypeList")
    public List<ItemType> itemTypeList() {
        // itemTypeList를 반환하는 메서드
        // 예시로 더미 데이터를 반환하도록 설정
        return List.of(
                new ItemType(1, "남성"),
                new ItemType(2, "여성"),
                new ItemType(3, "아동"),
                new ItemType(4, "가방"),
                new ItemType(5, "랭킹")
        );

    }
}
