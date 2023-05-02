package com.athletic.api.system.admin.dto;

import com.athletic.api.system.admin.entity.Admin;
import com.athletic.api.auth.util.SecurityUtil;
import com.athletic.api.utils.code.CodeDetail;
import com.athletic.api.utils.constant.Const;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequestDto {
    private String id;
    private String name;
    private String email;
    private String mobileNo;
    private String loginId;
    private String loginPw;
    private String changePw;
    private String authorityId;
    private String approveStatusCd;

    public Admin toAdmin() {
        return Admin.builder()
                .name(name)
                .email(email)
                .mobileNo(mobileNo)
                .loginId(loginId)
                .loginPw(loginPw)
                .authorityId(Const.AUTH_NO_MANAGER)
                .approveStatusCd(CodeDetail.APPROVE_STATUS_WAIT.getCode())
                .regId(Const.DEFAULT_ADMIN_ID)
                .regDt(LocalDateTime.now())
                .modId(Const.DEFAULT_ADMIN_ID)
                .modDt(LocalDateTime.now())
                .build();
    }

    public Admin toUpdateAdmin() {
        return Admin.builder()
                .id(id)
                .name(name)
                .email(email)
                .mobileNo(mobileNo)
                .loginId(loginId)
                .loginPw(loginPw)
                .authorityId(authorityId)
                .approveStatusCd(approveStatusCd)
                .modId(SecurityUtil.getCurrentId())
                .modDt(LocalDateTime.now())
                .build();
    }
}
