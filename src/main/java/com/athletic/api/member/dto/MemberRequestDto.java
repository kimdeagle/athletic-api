package com.athletic.api.member.dto;

import com.athletic.api.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {
    private String memberNo;
    private String memberNm;
    private String email;
    private String mobileNo;
    private String birthday;
    private String address;
    private String addressDtl;
    private String joinDt;
    private String regId;
    private Date regDt;
    private String modId;
    private Date modDt;

    public Member toMember() {
        return Member.builder()
                .memberNo(memberNo)
                .memberNm(memberNm)
                .email(email)
                .mobileNo(mobileNo)
                .birthday(birthday)
                .address(address)
                .addressDtl(addressDtl)
                .joinDt(joinDt)
                .regId(regId)
                .regDt(regDt)
                .modId(modId)
                .modDt(modDt)
                .build();
    }
}
