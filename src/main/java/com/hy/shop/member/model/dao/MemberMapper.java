package com.hy.shop.member.model.dao;

import com.hy.shop.member.model.vo.Account;
import com.hy.shop.member.model.vo.Member;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MemberMapper {

    int signup(Member insertMember);
    void save(Account account);
    Account findUser(String account);
}
