package com.hy.shop.member.model.vo;


import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Getter
public class Member {
        private int memberNo;
        private String memberId;
        private String memberPw;
        private String memberEmail;
        private String memberName;
        private String memberNickname;
        private String memberPhone;
        private String memberBirth;
        private String address;

    protected Member() {

    }


    private Member(MemberBuilder builder) {
        this.memberNo = builder.memberNo;
        this.memberId = builder.memberId;
        this.memberPw = builder.memberPw;
        this.memberEmail = builder.memberEmail;
        this.memberName = builder.memberName;
        this.memberNickname = builder.memberNickname;
        this.memberPhone = builder.memberPhone;
        this.memberBirth = builder.memberBirth;
        this.address = builder.address;
    }

    public String getEncodedPassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(memberPw);
    }

    public static class MemberBuilder {
        private int memberNo;
        private String memberId;
        private String memberPw;
        private String memberEmail;
        private String memberName;
        private String memberNickname;
        private String memberPhone;
        private String memberBirth;
        private String address;


        public MemberBuilder memberNo(int memberNo) {
            this.memberNo = memberNo;
            return this;
        }

        public MemberBuilder memberId(String memberId) {
            this.memberId = memberId;
            return this;
        }

        public MemberBuilder memberPw(String memberPw) {
            this.memberPw = memberPw;
            return this;
        }

        public MemberBuilder memberEmail(String memberEmail) {
            this.memberEmail = memberEmail;
            return this;
        }

        public MemberBuilder memberName(String memberName) {
            this.memberName = memberName;
            return this;
        }

        public MemberBuilder memberNickname(String memberNickname) {
            this.memberNickname = memberNickname;
            return this;
        }

        public MemberBuilder memberPhone(String memberPhone) {
            this.memberPhone = memberPhone;
            return this;
        }

        public MemberBuilder memberBirth(String memberBirth) {
            this.memberBirth = memberBirth;
            return this;
        }

        public MemberBuilder address(String address) {
            if (address != null && !address.trim().isEmpty()) {
                this.address = String.join(",,", address);
            } else {
                this.address = null;
            }
            return this;
        }



        public Member build() {
            return new Member(this);
        }


    }
}
