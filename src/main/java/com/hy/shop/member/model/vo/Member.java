package com.hy.shop.member.model.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
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

    }

