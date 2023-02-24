package com.athletic.api.member.dto;

import com.athletic.api.auth.util.SecurityUtil;
import com.athletic.api.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;

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
                .regId(SecurityUtil.getCurrentAdminNo())
                .regDt(LocalDateTime.now())
                .modId(SecurityUtil.getCurrentAdminNo())
                .modDt(LocalDateTime.now())
                .build();
    }

    public Member toUpdateMember() {
        return Member.builder()
                .memberNo(memberNo)
                .memberNm(memberNm)
                .email(email)
                .mobileNo(mobileNo)
                .birthday(birthday)
                .address(address)
                .addressDtl(addressDtl)
                .joinDt(joinDt)
                .modId(SecurityUtil.getCurrentAdminNo())
                .modDt(LocalDateTime.now())
                .build();
    }
}
