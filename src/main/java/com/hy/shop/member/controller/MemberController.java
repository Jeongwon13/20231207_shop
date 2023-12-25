package com.hy.shop.member.controller;

import com.hy.shop.commom.config.KakaoConfig;
import com.hy.shop.commom.config.NaverConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.ui.Model;
import com.hy.shop.member.model.service.MemberService;
import com.hy.shop.member.model.vo.Member;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@SessionAttributes({"loginMember"})
@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {
    private static Logger logger = LoggerFactory.getLogger(MemberController.class);

    private final KakaoConfig kakaoConfig;
    private final NaverConfig naverConfig;
    private final MemberService memberService;

    /**
     * Login
     * @param model
     * @param session
     * @param request
     * @return
     */
    @GetMapping("/login")
    public String login(Model model, HttpSession session, HttpServletRequest request) {
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
        model.addAttribute("kakao", kakaoConfig);
        return "member/login";
    }

    /**
     * 회원가입 시 동의하기 페이지
     * @return
     */
    @GetMapping("/signupAgree")
    public String signupAgree() {
        return "member/signupAgree";
    }

    /**
     * Sign Up Page
     * @return
     */
    @GetMapping("/signup")
    public String signup() {
        return "member/signup";
    }

    /**
     * Sign Up
     * @param member
     * @param address
     * @param redirect
     * @param request
     * @return
     */
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
        if (isSmsVerified) {
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


    /**
     * Login
     * @param inputMember
     * @param model
     * @param redirect
     * @param req
     * @param resp
     * @param remember
     * @return
     */
    @PostMapping("/login")
    public String login(@ModelAttribute Member inputMember, Model model, RedirectAttributes redirect,
                        HttpServletRequest req, HttpServletResponse resp, @RequestParam(name = "remember", required = false) String remember) {
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

    /**
     * Kakao Login(Call Back)
     * @param code
     * @param redirect
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/oauth")
    public String kakaoLogin(@RequestParam(value = "code", required = false) String code, RedirectAttributes redirect, HttpSession session) throws Exception {
        System.out.println("####### " + code);

        String access_Token = memberService.getAccessToken(code);
        Member userInfo = memberService.getUserInfo(access_Token);


        System.out.println("###access_Token#### : " + access_Token);
        log.info("userInfo:::: {} ", userInfo);

        session.setAttribute("userInfo", userInfo);
        String message = "";
        String path = "";

        if(userInfo != null) {
            message = userInfo.getMemberNickname()+"님 환영합니다.";
            path = "redirect:/";
        } else {
            message = "";
            path = "redirect:/member/login";
        }

        redirect.addFlashAttribute("message", message);

        return path;
    }


    /**
     * Naver Login
     * @param request
     * @return
     */
    @RequestMapping("/naverLogin")
    public String naverLogin(HttpServletRequest request) {
        String client_id = naverConfig.getClientId();
        String redirect_uri = naverConfig.getRedirectUri();
        String state = RandomStringUtils.randomAlphabetic(10);
        String login_url = "https://nid.naver.com/oauth2.0/authorize?response_type=code"
                + "&client_id=" + client_id
                + "&redirect_uri=" + redirect_uri
                + "&state=" + state;

        request.getSession().setAttribute("state", state);

        return "redirect:" + login_url;
    }

    /**
     * Naver Login(Call Back)
     * @param request
     * @param session
     * @param redirect
     * @return
     */
    @RequestMapping("/oauth/naver/login")
    public String naverRedirect(HttpServletRequest request, HttpSession session, RedirectAttributes redirect) {
        // 네이버에서 전달해준 code, state 값 가져오기
        String code = request.getParameter("code");
        String state = request.getParameter("state");

        // 세션에 저장해둔 state값 가져오기
        String session_state = String.valueOf(request.getSession().getAttribute("state"));

        // CSRF 공격 방지를 위해 state 값 비교
        if (!state.equals(session_state)) {
            System.out.println("세션 불일치");
            request.getSession().removeAttribute("state");
            return "/error";
        }

        String tokenURL = "https://nid.naver.com/oauth2.0/token";
        String client_id = naverConfig.getClientId();
        String client_secret = naverConfig.getClientSecret();

        // body data 생성
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("grant_type", "authorization_code");
        parameter.add("client_id", client_id);
        parameter.add("client_secret", client_secret);
        parameter.add("code", code);
        parameter.add("state", state);

        // request header 설정
        HttpHeaders headers = new HttpHeaders();
        // Content-type을 application/x-www-form-urlencoded 로 설정
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // header 와 body로 Request 생성
        HttpEntity<?> entity = new HttpEntity<>(parameter, headers);

        String message = "";
        String path = "";

        try {
            RestTemplate restTemplate = new RestTemplate();
            // 응답 데이터(json)를 Map 으로 받을 수 있도록 관련 메시지 컨버터 추가
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            // Post 방식으로 Http 요청
            // 응답 데이터 형식은 Hashmap 으로 지정
            ResponseEntity<HashMap> result = restTemplate.postForEntity(tokenURL, entity, HashMap.class);
            Map<String, String> resMap = result.getBody();

            // 리턴받은 access_token 가져오기
            String access_token = resMap.get("access_token");

            String userInfoURL = "https://openapi.naver.com/v1/nid/me";
            // Header에 access_token 삽입
            headers.set("Authorization", "Bearer "+access_token);

            // Request entity 생성
            HttpEntity<?> userInfoEntity = new HttpEntity<>(headers);

            // Post 방식으로 Http 요청
            // 응답 데이터 형식은 Hashmap 으로 지정
            ResponseEntity<HashMap> userResult = restTemplate.postForEntity(userInfoURL, userInfoEntity, HashMap.class);
            HashMap<String, Object> userResultMap = userResult.getBody();

            //응답 데이터 확인
            log.info("userResultMap:::: {}", userResultMap);
            Member naverLogin = memberService.naverLogin(userResultMap);
            log.info("naverLogin:::: {}", naverLogin);
            session.setAttribute("naverLogin", naverLogin);

            if(naverLogin != null) {
                message = naverLogin.getMemberName()+"님 환영합니다.";
                path = "redirect:/";
            } else {
                message = "";
                path = "redirect:/member/login";
            }
            redirect.addFlashAttribute("message", message);
            return path;

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 세션에 저장된 state 값 삭제
        request.getSession().removeAttribute("state");

        return path;
    }

}