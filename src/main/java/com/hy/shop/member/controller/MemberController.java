package com.hy.shop.member.controller;

import com.hy.shop.member.model.service.MemberService;
import com.hy.shop.member.model.vo.Member;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/member")
@Controller
public class MemberController {

    private final MemberService memberService;

    // 생성자 주입
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/signupAgree")
    public String signupAgree() {
        return "member/signupAgree";
    }

    @GetMapping("/signup")
    public String signup() {
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute Member member, String[] address, RedirectAttributes redirect, HttpServletRequest request) {

        int result = memberService.signup(member);

        String message = "";
        String path = "";
        //String confirmScript = "";

        if(result > 0) {
            message = "hy의 가족이 되신 것을 환영합니다.";
            path = "redirect:/";
        } else {
            message = "회원가입에 실패하였습니다. 다시 시도해주세요.";
            path = "redirect:/member/signup";
        }

        redirect.addFlashAttribute("message", message);
        //redirect.addFlashAttribute("confirmScript", confirmScript);

        return path;
    }
}
