package com.athletic.api.dues.dto;

import com.athletic.api.dues.entity.Dues;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DuesResponseDto {
    private String id;

    private String inOut;

    private LocalDateTime startDt;

    private LocalDateTime endDt;

    private String title;

    private String description;

    private Long amount;

    public static DuesResponseDto of(Dues dues) {
        return DuesResponseDto.builder()
                .id(dues.getId())
                .inOut(dues.getInOut())
                .startDt(dues.getStartDt())
                .endDt(dues.getEndDt())
                .title(dues.getTitle())
                .description(dues.getDescription())
                .amount(dues.getAmount())
                .build();
    }

}
