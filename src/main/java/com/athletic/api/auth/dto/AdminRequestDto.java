package com.athletic.api.auth.dto;

import com.athletic.api.auth.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminRequestDto {
    private String adminNm;
    private String email;
    private String mobileNo;
    private String loginId;
    private String loginPw;
    private String authNo;
    private String aprvStCd;
    private String regId;
    private Date regDt;
    private String modId;
    private Date modDt;

    public Admin toAdmin() {
        return Admin.builder()
                .adminNm(adminNm)
                .email(email)
                .mobileNo(mobileNo)
                .loginId(loginId)
                .loginPw(loginPw)
                .authNo(authNo)
                .aprvStCd(aprvStCd)
                .regId(regId)
                .regDt(regDt)
                .modId(modId)
                .modDt(modDt)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(loginId, loginPw);
    }
}
