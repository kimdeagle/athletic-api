package com.athletic.api.authority.dto;

import com.athletic.api.authority.entity.AuthorityMenu;
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
public class AuthorityMenuResponseDto {
    private String authNo;
    private String menuNo;
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;

    public static AuthorityMenuResponseDto of(AuthorityMenu authorityMenu) {
        return AuthorityMenuResponseDto.builder()
                .authNo(authorityMenu.getAuthNo())
                .menuNo(authorityMenu.getMenuNo())
                .regId(authorityMenu.getRegId())
                .regDt(authorityMenu.getRegDt())
                .modId(authorityMenu.getModId())
                .modDt(authorityMenu.getModDt())
                .build();
    }

}
