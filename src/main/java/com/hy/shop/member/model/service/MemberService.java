package com.hy.shop.member.model.service;

import com.hy.shop.commom.util.SHA256;
import com.hy.shop.member.controller.MemberController;
import com.hy.shop.member.model.dao.MemberMapper;
import com.hy.shop.member.model.vo.Member;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private static Logger logger = LoggerFactory.getLogger(MemberController.class);

    private final MemberMapper memberMapper;

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


}
