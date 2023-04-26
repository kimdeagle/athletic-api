package com.athletic.api.system.authority.dto;

import com.athletic.api.system.authority.entity.Authority;
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
public class AuthorityResponseDto extends BaseResponseDto {
    private String id;
    private String name;
    private String displayName;

    public static AuthorityResponseDto of(Authority authority) {
        return AuthorityResponseDto.builder()
                .id(authority.getId())
                .name(authority.getName())
                .displayName(authority.getDisplayName())
                .regId(authority.getRegId())
                .regDt(authority.getRegDt())
                .modId(authority.getModId())
                .modDt(authority.getModDt())
                .build();
    }

}
