package com.athletic.api.member.dto;

import com.athletic.api.common.dto.BaseResponseDto;
import com.athletic.api.member.entity.Member;
import com.athletic.api.utils.excel.style.ExcelCellStyle;
import com.athletic.api.utils.excel.ExcelDownloadColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto extends BaseResponseDto {
    private String id;

    @ExcelDownloadColumn(headerName = "회원명", sort = 0, bodyStyle = ExcelCellStyle.CENTER_BODY)
    private String name;

    @ExcelDownloadColumn(headerName = "이메일", sort = 1, width = 24)
    private String email;

    @ExcelDownloadColumn(headerName = "휴대폰 번호", sort = 2, width = 18, bodyStyle = ExcelCellStyle.CENTER_BODY)
    private String mobileNo;

    @ExcelDownloadColumn(headerName = "생년월일", sort = 3, width = 15, bodyStyle = ExcelCellStyle.CENTER_BODY)
    private String birthday;

    @ExcelDownloadColumn(headerName = "주소", sort = 4, width = 30)
    private String address;

    private String addressDtl;

    @ExcelDownloadColumn(headerName = "입회일자", sort = 5, width = 15, bodyStyle = ExcelCellStyle.CENTER_BODY)
    private String joinDt;

    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .mobileNo(member.getMobileNo())
                .birthday(member.getBirthday())
                .address(member.getAddress())
                .addressDtl(member.getAddressDtl())
                .joinDt(member.getJoinDt())
                .regId(member.getRegId())
                .regDt(member.getRegDt())
                .modId(member.getModId())
                .modDt(member.getModDt())
                .build();
    }

}
