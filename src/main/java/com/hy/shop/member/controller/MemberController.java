package com.hy.shop.member.controller;

import com.hy.shop.commom.config.KakaoProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import com.hy.shop.member.model.service.MemberService;
import com.hy.shop.member.model.vo.Member;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Log
@SessionAttributes({"loginMember"})
@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {
    private static Logger logger = LoggerFactory.getLogger(MemberController.class);

    private final KakaoProperties kakaoProperties;

    private final MemberService memberService;

    @GetMapping("/login")
    public String login(Model model, HttpSession session,HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("remember")) {
                    if (cookie.getValue() != null && !cookie.getValue().isEmpty()) {
                        model.addAttribute("remember", "checked");
                    }
                    break;
                }
            }
        }
        model.addAttribute("kakao", kakaoProperties);
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

        log.info("MEMBER의 값info?: " + insertMember.getMemberId());

        boolean isSmsVerified = true;
        if(isSmsVerified) {
            int result = memberService.signup(insertMember);

            log.info("result 값info?: " + result);

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
        } else {
            redirect.addFlashAttribute("message", "SMS 인증을 먼저 완료해주세요.");
            return "redirect:/member/signup";
        }
    }



    @PostMapping("/login")
    public String login(@ModelAttribute Member inputMember, Model model, RedirectAttributes redirect,
                        HttpServletRequest req, HttpServletResponse resp, @RequestParam(name="remember", required=false) String remember) {
        Member loginMember = memberService.login(inputMember);

        if (loginMember != null) {
            logger.info("로그인 성공: {}", loginMember.getMemberId());

            model.addAttribute("loginMember", loginMember);
            if (loginMember.getMemberId() != null) {
                Cookie cookie = new Cookie("remember", loginMember.getMemberId());
                if (remember != null) {
                    cookie.setMaxAge(60 * 60 * 24 * 365); //1년
                    cookie.setValue(loginMember.getMemberId());
                    model.addAttribute("remember", "checked");
                } else {
                    cookie.setMaxAge(0);
                    cookie.setValue("");
                }
                cookie.setPath(req.getContextPath() + "/member/login");
                resp.addCookie(cookie);
            }
                return "redirect:/";
        } else {
            redirect.addFlashAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");

            logger.info("로그인 실패 {}", inputMember.getMemberId());
            return "redirect:/member/login";
        }
    }


}
