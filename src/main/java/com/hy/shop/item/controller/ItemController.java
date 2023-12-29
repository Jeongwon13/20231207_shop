package com.hy.shop.item.controller;

import com.hy.shop.item.model.service.ItemService;
import com.hy.shop.item.model.vo.ItemType;
import com.hy.shop.member.model.vo.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/item")
@Controller
public class ItemController {

    private final ItemService itemService;

    /**
     * 상품 페이지 이동
     * @param itemCode
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/list/{itemCode}")
    public String itemList(@PathVariable(name = "itemCode") int itemCode, Model model, HttpSession session) {
        List<ItemType> itemOneTypeList = itemService.selectOneItemType(itemCode);
        log.info("itemOneTypeList:::: {} ", itemOneTypeList);
        Member adminMember = (Member) session.getAttribute("loginMember");
        model.addAttribute("itemOneTypeList", itemOneTypeList);
        model.addAttribute("adminMember", adminMember);
        log.info("adminMember:::: {} ", adminMember);
        return "item/itemList";
    }

    /**
     * 상품 등록 폼 열기
     */
    @GetMapping("/list/{itemCode}/registration")
    public String itemResitration() {
        return "item/registration";
    }

}
