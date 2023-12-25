package com.hy.shop.member.model.dao;

import com.hy.shop.member.model.vo.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;


@Mapper
public interface MemberMapper {

    int signup(Member insertMember);
    Member login(Member inputMember);

    Member findKakao(Map<String,Object> userInfo);
    void insertKakao(Map<String,Object> userInfo);

    Member findNaver(Map<String, Object> userResultMap);
    void insertNaver(Map<String, Object> userResultMap);

}

