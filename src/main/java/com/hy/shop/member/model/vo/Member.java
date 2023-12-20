package com.hy.shop.member.model.vo;

import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

@Getter
@Builder
@Alias("Member")
public class Member {
    private Long memberNo;
    private String memberId;
    private String memberPw;
    private String memberEmail;
    private String memberName;
    private String memberNickname;
    private String memberPhone;
    private String memberBirth;
    private String address;
    private String salt;

}
