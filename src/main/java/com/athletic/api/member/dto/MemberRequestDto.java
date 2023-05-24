package com.athletic.api.member.dto;

import com.athletic.api.security.utils.SecurityUtils;
import com.athletic.api.exception.ErrorMessage;
import com.athletic.api.member.entity.Member;
import com.athletic.api.utils.constant.Const;
import com.athletic.api.utils.excel.ExcelUploadColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {
    private String id;

    @ExcelUploadColumn(colIndex = 1, required = true)
    private String name;

    @ExcelUploadColumn(colIndex = 2, validationRegex = Const.EMAIL_REGEX, errorMessage = ErrorMessage.ExcelUpload.INVALID_EMAIL)
    private String email;

    @ExcelUploadColumn(colIndex = 3, required = true, validationRegex = Const.MOBILE_NO_REGEX, errorMessage = ErrorMessage.ExcelUpload.INVALID_MOBILE_NO)
    private String mobileNo;

    @ExcelUploadColumn(colIndex = 4, validationRegex = Const.DATE_REGEX, errorMessage = ErrorMessage.ExcelUpload.INVALID_BIRTHDAY)
    private String birthday;

    @ExcelUploadColumn(colIndex = 5)
    private String address;

    @ExcelUploadColumn(colIndex = 6)
    private String addressDtl;

    @ExcelUploadColumn(colIndex = 7, required = true, validationRegex = Const.DATE_REGEX, errorMessage = ErrorMessage.ExcelUpload.INVALID_JOIN_DT)
    private String joinDt;

    public Member toMember() {
        return Member.builder()
                .name(name)
                .email(email)
                .mobileNo(mobileNo)
                .birthday(birthday)
                .address(address)
                .addressDtl(addressDtl)
                .joinDt(joinDt)
                .regId(SecurityUtils.getCurrentId())
                .regDt(LocalDateTime.now())
                .modId(SecurityUtils.getCurrentId())
                .modDt(LocalDateTime.now())
                .build();
    }

    public Member toUpdateMember() {
        return Member.builder()
                .id(id)
                .name(name)
                .email(email)
                .mobileNo(mobileNo)
                .birthday(birthday)
                .address(address)
                .addressDtl(addressDtl)
                .joinDt(joinDt)
                .modId(SecurityUtils.getCurrentId())
                .modDt(LocalDateTime.now())
                .build();
    }
}
