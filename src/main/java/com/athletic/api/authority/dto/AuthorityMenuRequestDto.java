package com.athletic.api.authority.dto;

import com.athletic.api.auth.util.SecurityUtil;
import com.athletic.api.authority.entity.AuthorityMenu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityMenuRequestDto {
    private String authNo;
    private String menuNo;
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;

    public AuthorityMenu toAuthorityMenu() {
        return AuthorityMenu.builder()
                .authNo(authNo)
                .menuNo(menuNo)
                .regId(SecurityUtil.getCurrentAdminNo())
                .regDt(LocalDateTime.now())
                .modId(SecurityUtil.getCurrentAdminNo())
                .modDt(LocalDateTime.now())
                .build();
    }
}
