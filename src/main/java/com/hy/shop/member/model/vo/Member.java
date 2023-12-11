package com.hy.shop.member.model.vo;

import com.hy.shop.commom.util.SHA256;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Member {
    private final Long memberNo;
    private final String memberId;
    private final String memberPw;
    private final String memberEmail;
    private final String memberName;
    private final String memberNickname;
    private final String memberPhone;
    private final String memberBirth;
    private final String address;
    private final String salt;

    public static MemberBuilder builderWithPassword(String password) {
        return new MemberBuilder().memberPw(SHA256.getEncrypt(password, SHA256.generateSalt()));
    }
}
