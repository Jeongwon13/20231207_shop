package com.hy.shop.member.model.service;

import com.hy.shop.member.model.dao.MemberMapper;
import com.hy.shop.member.model.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    @Autowired
    MemberMapper memberMapper;

    @Transactional
    public int signup(Member insertMember) {
        return memberMapper.signup(insertMember);
    }


}
