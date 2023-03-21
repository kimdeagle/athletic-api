package com.athletic.api.admin.dto;

import com.athletic.api.admin.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseDto {
    private String id;
    private String name;
    private String email;
    private String mobileNo;
    private String loginId;
    private String authNo;
    private String aprvStCd;
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;

    public static AdminResponseDto of(Admin admin) {
        return AdminResponseDto.builder()
                .id(admin.getId())
                .name(admin.getName())
                .email(admin.getEmail())
                .mobileNo(admin.getMobileNo())
                .loginId(admin.getLoginId())
                .authNo(admin.getAuthNo())
                .aprvStCd(admin.getAprvStCd())
                .regId(admin.getRegId())
                .regDt(admin.getRegDt())
                .modId(admin.getModId())
                .modDt(admin.getModDt())
                .build();
    }

}
