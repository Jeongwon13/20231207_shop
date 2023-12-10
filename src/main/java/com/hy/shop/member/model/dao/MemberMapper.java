package com.hy.shop.member.model.dao;

import com.hy.shop.member.model.vo.Member;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MemberMapper {

    int signup(Member insertMember);

}
