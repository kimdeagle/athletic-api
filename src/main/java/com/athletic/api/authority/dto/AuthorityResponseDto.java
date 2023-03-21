package com.athletic.api.authority.dto;

import com.athletic.api.authority.entity.Authority;
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
public class AuthorityResponseDto {
    private String id;
    private String name;
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;

    public static AuthorityResponseDto of(Authority authority) {
        return AuthorityResponseDto.builder()
                .id(authority.getId())
                .name(authority.getName())
                .regId(authority.getRegId())
                .regDt(authority.getRegDt())
                .modId(authority.getModId())
                .modDt(authority.getModDt())
                .build();
    }

}
