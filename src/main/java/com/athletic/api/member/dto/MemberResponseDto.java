package com.athletic.api.member.dto;

import com.athletic.api.member.entity.Member;
import com.athletic.api.util.excel.ExcelAlign;
import com.athletic.api.util.excel.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    @ExcelColumn(headerName = "회원번호", align = ExcelAlign.RIGHT_BOTTOM, width = 8000)
    private String memberNo;
    @ExcelColumn(headerName = "회원명")
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
