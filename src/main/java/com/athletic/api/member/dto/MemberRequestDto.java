package com.athletic.api.member.dto;

import com.athletic.api.auth.util.SecurityUtil;
import com.athletic.api.exception.ErrorMessage;
import com.athletic.api.member.entity.Member;
import com.athletic.api.util.constant.Const;
import com.athletic.api.util.excel.ExcelUploadColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {
    private String memberNo;

    @ExcelUploadColumn(colIndex = 1, required = true)
    private String memberNm;

    @ExcelUploadColumn(colIndex = 2, validationRegex = Const.EMAIL_REGEX, errorMessage = ErrorMessage.ExcelUpload.INVALID_EMAIL)
    private String email;

    @ExcelUploadColumn(colIndex = 3, required = true, validationRegex = Const.MOBILE_NO_REGEX, errorMessage = ErrorMessage.ExcelUpload.INVALID_MOBILE_NO)
    private String mobileNo;

    @ExcelUploadColumn(colIndex = 4, validationRegex = Const.YYYYMMDD_REGEX, errorMessage = ErrorMessage.ExcelUpload.INVALID_BIRTHDAY)
    private String birthday;

    @ExcelUploadColumn(colIndex = 5)
    private String address;

    @ExcelUploadColumn(colIndex = 6)
    private String addressDtl;

    @ExcelUploadColumn(colIndex = 7, required = true, validationRegex = Const.YYYYMMDD_REGEX, errorMessage = ErrorMessage.ExcelUpload.INVALID_JOIN_DT)
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
