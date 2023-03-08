package com.athletic.api.member.dto;

import com.athletic.api.member.entity.Member;
import com.athletic.api.util.excel.style.ExcelCellStyle;
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
    @ExcelColumn(headerName = "회원번호", sort = 0, width = 15)
    private String memberNo;
    @ExcelColumn(headerName = "회원명", sort = 1, bodyStyle = ExcelCellStyle.TEST_BODY)
    private String memberNm;
    private String email;
    private String mobileNo;
    @ExcelColumn(headerName = "생년월일", sort = 2)
    private String birthday;
    private String address;
    private String addressDtl;
    @ExcelColumn(headerName = "입회일자", sort = 3)
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
