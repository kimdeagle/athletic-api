package com.athletic.api.auth.dto;

import com.athletic.api.auth.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminResponseDto {
    private String adminNm;
    private String email;
    private String authNo;
    private String aprvStCd;
    private TokenDto tokenDto;

    public static AdminResponseDto of(Admin admin) {
        return AdminResponseDto.builder()
                .adminNm(admin.getAdminNm())
                .email(admin.getEmail())
                .authNo(admin.getAuthNo())
                .aprvStCd(admin.getAprvStCd())
                .build();
    }

}
