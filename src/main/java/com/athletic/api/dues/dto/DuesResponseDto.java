package com.athletic.api.dues.dto;

import com.athletic.api.common.dto.BaseResponseDto;
import com.athletic.api.dues.entity.Dues;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DuesResponseDto extends BaseResponseDto {
    private String id;

    private String inOutCd;

    private String inOutDtlCd;

    private LocalDateTime startDt;

    private LocalDateTime endDt;

    private String title;

    private String description;

    private Long amount;

    public static DuesResponseDto of(Dues dues) {
        return DuesResponseDto.builder()
                .id(dues.getId())
                .inOutCd(dues.getInOutCd())
                .inOutDtlCd(dues.getInOutDtlCd())
                .startDt(dues.getStartDt())
                .endDt(dues.getEndDt())
                .title(dues.getTitle())
                .description(dues.getDescription())
                .amount(dues.getAmount())
                .regId(dues.getRegId())
                .regDt(dues.getRegDt())
                .modId(dues.getModId())
                .modDt(dues.getModDt())
                .build();
    }

}
