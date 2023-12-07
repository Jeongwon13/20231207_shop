package com.hy.shop.item.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {

    @GetMapping("/itemList")
    public String itemList() {
        return "item/itemList";
    }
}
