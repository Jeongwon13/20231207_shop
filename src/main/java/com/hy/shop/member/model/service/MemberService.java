package com.hy.shop.member.model.service;

import com.hy.shop.member.MemberMapper;
import com.hy.shop.member.model.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService implements MemberMapper {
    @Autowired
    MemberMapper memberMapper;

    @Transactional
    public int signup(Member member) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Member memberBuilder = new Member.MemberBuilder()
                .memberPw("rawPassword")
                .build();

        int result = memberMapper.signup(memberBuilder);
        return result;
    }
}
