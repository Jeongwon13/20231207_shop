package com.hy.shop.member.model.service;

import com.hy.shop.commom.util.SHA256;
import com.hy.shop.member.model.dao.MemberMapper;
import com.hy.shop.member.model.vo.Member;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    @Transactional
    public int signup(Member insertMember) {

        Member encryptedMember = Member.builderWithPassword(insertMember.getMemberPw())
                .memberId(insertMember.getMemberId())
                .memberEmail(insertMember.getMemberEmail())
                .memberName(insertMember.getMemberName())
                .memberNickname(insertMember.getMemberNickname())
                .memberPhone(insertMember.getMemberPhone())
                .memberBirth(insertMember.getMemberBirth())
                .address(insertMember.getAddress())
                .build();

        return memberMapper.signup(encryptedMember);
    }
}
