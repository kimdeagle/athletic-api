package com.athletic.api.system.authority.dto;

import com.athletic.api.security.utils.SecurityUtils;
import com.athletic.api.system.authority.entity.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityRequestDto {
    private String id;
    private String name;
    private String displayName;

    public Authority toAuthority() {
        return Authority.builder()
                .name(name)
                .displayName(displayName)
                .regId(SecurityUtils.getCurrentId())
                .regDt(LocalDateTime.now())
                .modId(SecurityUtils.getCurrentId())
                .modDt(LocalDateTime.now())
                .build();
    }

    public Authority toUpdateAuthority() {
        return Authority.builder()
                .id(id)
                .name(name)
                .displayName(displayName)
                .modId(SecurityUtils.getCurrentId())
                .modDt(LocalDateTime.now())
                .build();
    }
}
