package com.hy.shop.mypage.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j
@RequestMapping("/mypage")
@Controller
public class MyPageController {

    /**
     * 마이페이지 Main 이동
     */
    @GetMapping("/main")
    public String myPage() {
        return "mypage/myPage";
    }


}
