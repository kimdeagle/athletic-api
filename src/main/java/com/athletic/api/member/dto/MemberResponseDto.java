package com.athletic.api.member.dto;

import com.athletic.api.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    private String memberNo;
    private String memberNm;
    private String email;
    private String mobileNo;
    private String birthday;
    private String address;
    private String addressDtl;
    private String joinDt;

    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .memberNo(member.getMemberNo())
                .memberNm(member.getMemberNm())
                .email(member.getEmail())
                .mobileNo(member.getMobileNo())
                .birthday(member.getBirthday())
                .address(member.getAddress())
                .addressDtl(member.getAddressDtl())
                .joinDt(member.getJoinDt())
                .build();
    }

}
