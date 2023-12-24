package com.hy.shop.member.model.service;
import org.json.JSONObject;
import com.google.gson.JsonElement;
import com.hy.shop.commom.config.KakaoConfig;
import com.hy.shop.commom.config.NaverConfig;
import com.hy.shop.commom.util.SHA256;
import com.hy.shop.member.controller.MemberController;
import com.hy.shop.member.model.dao.MemberMapper;
import com.hy.shop.member.model.vo.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private static Logger logger = LoggerFactory.getLogger(MemberController.class);
    private final KakaoConfig kakaoConfig;
    private final NaverConfig naverConfig;
    private final MemberMapper memberMapper;

    /**
     * 일반 회원가입
     *
     * @param insertMember
     * @return
     */
    public int signup(Member insertMember) {
        String salt = SHA256.generateSalt();
        String hashedPassword = SHA256.getEncrypt(insertMember.getMemberPw(), salt);

        Member encryptedMember = Member.builder()
                .memberId(insertMember.getMemberId())
                .memberPw(hashedPassword)
                .memberEmail(insertMember.getMemberEmail())
                .memberName(insertMember.getMemberName())
                .memberNickname(insertMember.getMemberNickname())
                .memberPhone(insertMember.getMemberPhone())
                .memberBirth(insertMember.getMemberBirth())
                .address(insertMember.getAddress())
                .salt(salt)
                .build();
        return memberMapper.signup(encryptedMember);
    }

    /**
     * 일반 로그인
     *
     * @param inputMember
     * @return
     */
    public Member login(Member inputMember) {
        Member savePw = memberMapper.login(inputMember); // 지금 DB의 ID, PW, SALT 값 담김.

        if (savePw != null) {
            String hashedPassword = SHA256.getEncrypt(inputMember.getMemberPw(), savePw.getSalt());

            if (hashedPassword.equals(savePw.getMemberPw())) {
                logger.info("비밀번호 비교: {} :::: {}", hashedPassword, savePw.getMemberPw());
                return savePw;
            }
        }
        logger.debug("로그인 실패 ID: {}", inputMember.getMemberId());

        return null;
    }

    /**
     * 카카오 로그인
     * @param code
     * @return
     */
    public String getAccessToken(String code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로 변경을 해주세요

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            // BufferedWriter 간단하게 파일을 끊어서 보내기로 토큰값을 받아오기위해 전송

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=").append(kakaoConfig.getApiKey());  //본인이 발급받은 key
            sb.append("&redirect_uri=").append(kakaoConfig.getRedirectUri());     // 본인이 설정해 놓은 경로
            sb.append("&code=").append(code);
            bw.write(sb.toString());
            bw.flush();

            //    결과 코드가 200이라면 성공
            // 여기서 안되는경우가 많이 있어서 필수 확인 !! **
            int responseCode = conn.getResponseCode();
            out.println("responseCode : " + responseCode + "확인");

            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            out.println("response body:::: : " + result + "결과");

            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            out.println("access_token : " + access_Token);
            out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    /**
     * 카카오 로그인
     * @param access_Token
     * @return
     */
    public Member getUserInfo(String access_Token) {

        HashMap<String, Object> userInfo = new HashMap<>();

        String requestURL = "https://kapi.kakao.com/v2/user/me";
        
        Member kakaoResult = null;
        try {
            URL url = new URL(requestURL); //1.url 객체만들기
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //2.url 에서 url connection 만들기
            conn.setRequestMethod("GET"); // 3.URL 연결구성
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            //키값, 속성 적용
            int responseCode = conn.getResponseCode(); //서버에서 보낸 http 상태코드 반환
            out.println("responseCode :" + responseCode);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            // 버퍼를 사용하여 읽는 것
            String line = "";
            String result = "";
            while ((line = buffer.readLine()) != null) {
                result += line;
            }
            //readLine()) ==> 입력 String 값으로 리턴값 고정

            out.println("response body :" + result);

            // 읽엇으니깐 데이터꺼내오기
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result); //Json element 문자열변경
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            JsonElement nicknameElement = properties.getAsJsonObject().get("nickname");
            String nickname = (nicknameElement != null && !nicknameElement.isJsonNull()) ? nicknameElement.getAsString() : null;

            JsonElement emailElement = kakao_account.getAsJsonObject().get("email");
            String email = (emailElement != null && !emailElement.isJsonNull()) ? emailElement.getAsString() : null;
            log.info("nickname:::: {}", nickname);

            log.info("email:::: {}", email);
            userInfo.put("nickname", nickname);
            userInfo.put("email", email);

            kakaoResult = memberMapper.findKakao(userInfo);

            if (kakaoResult == null) {
                // result null 이면 정보가 저장 안되어 있는 거라서 정보를 저장.
                memberMapper.insertKakao(userInfo);
                // 저장하기 위해 repository로 이동
                return memberMapper.findKakao(userInfo);
                // 정보 저장 후 컨트롤러에 정보를 보냄
                // result를 리턴으로 보내면 null이 리턴되므로 위 코드를 사용.
            } else {
                // 정보가 있으므로 result를 리턴함
                return kakaoResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return kakaoResult;
    }



    /**
     * 카카오 로그아웃
     * @param accessToken
     */
    public void kakaoLogout(String accessToken) {
        String reqUrl = "https://kapi.kakao.com/v1/user/logout";

        try{
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
            log.info("[KakaoApi.kakaoLogout] responseCode : {}",  responseCode);

            BufferedReader br;
            if (responseCode >= 200 && responseCode <= 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line = "";
            StringBuilder responseSb = new StringBuilder();
            while((line = br.readLine()) != null){
                responseSb.append(line);
            }
            String result = responseSb.toString();
            log.info("kakao logout - responseBody = {}", result);



        }catch (Exception e){
            e.printStackTrace();
        }
    }


}