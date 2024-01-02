package com.hy.shop.item.controller;

import com.hy.shop.item.model.service.ItemService;
import com.hy.shop.item.model.vo.Item;
import com.hy.shop.item.model.vo.ItemType;
import com.hy.shop.member.model.vo.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/item")
@Controller
public class ItemController {

    private final ItemService itemService;

    /**
     * 상품 페이지 이동
     * @param itemTypeCd
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/list/{itemTypeCode}")
    public String itemList(@PathVariable(name = "itemTypeCode") int itemTypeCd, Model model, HttpSession session) {
        List<ItemType> itemOneTypeList = itemService.selectOneItemType(itemTypeCd);
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
    @GetMapping("/list/{itemTypeCode}/registration")
    public String itemRegistration(@PathVariable(name = "itemTypeCode") String itemTypeCd, Model model) {
            model.addAttribute("itemTypeCode", itemTypeCd);

            log.info("itemTypeCode:::: {}", itemTypeCd);
        return "item/registration";
    }

    /**
     * 상품 등록
     * @param imageList
     * @param req
     * @param ra
     * @return
     * @throws IOException
     */
    @PostMapping("/list/register")
    public String itemInsert ( @RequestParam(value="images", required=false) List<MultipartFile> imageList
            , HttpServletRequest req
            , RedirectAttributes ra
            , Item item
    ) throws IOException {

        String uploadsPath = "C:\\Users\\jeongwon\\Documents\\shop\\shop\\src\\main\\resources\\static";
       // String webPath = "/resources/images/thumbnail/";
        String folderPath = uploadsPath + "\\images\\items\\";


        Map<String, Object> params = new HashMap<>();
        log.info("folderPath:::: {}" , folderPath);

        params.put("itemName", item.getItemName());
        params.put("itemPrice", item.getItemPrice());
        params.put("stockQuantity", item.getStockQuantity());
        params.put("itemTypeCd", item.getItemTypeCd());
        params.put("itemCategoryId", item.getItemCategoryId());
        params.put("itemContent", item.getItemContent());

        log.info("params::::{}", params);

        int itemId = itemService.insertItem(params, imageList, folderPath);

        log.info("itemId:::: {}" , itemId );
        log.info("itemDetail:::: {}" , item);

        String path = null;
        String message = null;

        if(itemId > 0) {

            path = "/";
            message = "상품이 등록되었습니다.";

        }else {
            path = "./";
            message = "상품 등록 실패 ...";
        }

        ra.addFlashAttribute("message", message);

        return "redirect:" + path;

    }



}
