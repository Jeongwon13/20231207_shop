package com.hy.shop.member.controller;

import com.hy.shop.member.model.service.MemberService;
import com.hy.shop.member.model.vo.Member;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {
    private static Logger logger = LoggerFactory.getLogger(MemberController.class);

    private final MemberService memberService;

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
    public String signup(@ModelAttribute Member member, @RequestParam(name = "address") String[] address, RedirectAttributes redirect, HttpServletRequest request) {

        String joinedAddress = (address != null) ? String.join(",,", address) : "";

        Member insertMember = Member.builder()
                .memberId(member.getMemberId())
                .memberPw(member.getMemberPw())
                .memberEmail(member.getMemberEmail())
                .memberName(member.getMemberName())
                .memberNickname(member.getMemberNickname())
                .memberPhone(member.getMemberPhone())
                .memberBirth(member.getMemberBirth())
                .address(joinedAddress)
                .build();

        logger.info("MEMBER의 값info?: " + insertMember.getMemberId());

        int result = memberService.signup(insertMember);

        logger.info("result 값info?: " + result);

        String message = "";
        String path = "";

        if (result > 0) {
            message = "hy의 가족이 되신 것을 환영합니다.";
            path = "redirect:/";
        } else {
            message = "회원가입에 실패하였습니다. 다시 시도해주세요.";
            path = "redirect:/member/signup";
        }

        redirect.addFlashAttribute("message", message);

        return path;
    }



}

