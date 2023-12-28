package com.hy.shop.item.controller;

import com.hy.shop.item.model.service.ItemService;
import com.hy.shop.item.model.vo.ItemType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/item")
@Controller
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/list/{itemCode}")
    public String itemList() {
        return "item/itemList";
    }



}
