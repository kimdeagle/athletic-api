package com.athletic.api.admin.dto;

import com.athletic.api.admin.entity.Admin;
import com.athletic.api.auth.util.SecurityUtil;
import com.athletic.api.util.constant.Const;
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
    private String adminNo;
    private String adminNm;
    private String email;
    private String mobileNo;
    private String loginId;
    private String loginPw;
    private String changePw;
    private String authNo;
    private String aprvStCd;
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;

    public Admin toAdmin() {
        return Admin.builder()
                .adminNo(adminNo)
                .adminNm(adminNm)
                .email(email)
                .mobileNo(mobileNo)
                .loginId(loginId)
                .loginPw(loginPw)
                .authNo(Const.AUTH_NO_MANAGER)
                .aprvStCd(Const.APRV_ST_CD_WAIT)
                .regId(Const.DEFAULT_ADMIN_ID)
                .regDt(LocalDateTime.now())
                .modId(Const.DEFAULT_ADMIN_ID)
                .modDt(LocalDateTime.now())
                .build();
    }

    public Admin toUpdateAdmin() {
        return Admin.builder()
                .adminNo(adminNo)
                .adminNm(adminNm)
                .email(email)
                .mobileNo(mobileNo)
                .loginId(loginId)
                .loginPw(loginPw)
                .authNo(authNo)
                .aprvStCd(aprvStCd)
                .modId(SecurityUtil.getCurrentAdminNo())
                .modDt(LocalDateTime.now())
                .build();
    }
}
