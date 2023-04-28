package com.athletic.api.system.admin.dto;

import com.athletic.api.system.admin.entity.Admin;
import com.athletic.api.common.dto.BaseResponseDto;
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
public class AdminResponseDto extends BaseResponseDto {
    private String id;
    private String name;
    private String email;
    private String mobileNo;
    private String loginId;
    private String authorityId;
    private String approveStatusCd;

    private String authorityDisplayName;

    public static AdminResponseDto of(Admin admin) {
        return AdminResponseDto.builder()
                .id(admin.getId())
                .name(admin.getName())
                .email(admin.getEmail())
                .mobileNo(admin.getMobileNo())
                .loginId(admin.getLoginId())
                .authorityId(admin.getAuthorityId())
                .approveStatusCd(admin.getApproveStatusCd())
                .regId(admin.getRegId())
                .regDt(admin.getRegDt())
                .modId(admin.getModId())
                .modDt(admin.getModDt())
                .build();
    }

}
