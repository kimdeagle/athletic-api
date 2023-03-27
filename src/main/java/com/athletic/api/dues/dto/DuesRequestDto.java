package com.athletic.api.dues.dto;

import com.athletic.api.auth.util.SecurityUtil;
import com.athletic.api.dues.entity.Dues;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DuesRequestDto {
    private String id;

    private String inOut;

    private LocalDateTime startDt;

    private LocalDateTime endDt;

    private String title;

    private String description;

    private Long amount;

    public Dues toDues() {
        return Dues.builder()
                .inOut(inOut)
                .startDt(startDt)
                .endDt(endDt)
                .title(title)
                .description(description)
                .amount(amount)
                .regId(SecurityUtil.getCurrentId())
                .regDt(LocalDateTime.now())
                .modId(SecurityUtil.getCurrentId())
                .modDt(LocalDateTime.now())
                .build();
    }

    public Dues toUpdateDues() {
        return Dues.builder()
                .id(id)
                .inOut(inOut)
                .startDt(startDt)
                .endDt(endDt)
                .title(title)
                .description(description)
                .amount(amount)
                .modId(SecurityUtil.getCurrentId())
                .modDt(LocalDateTime.now())
                .build();
    }
}
