package com.athletic.api.util.excel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExcelDownloadSearchCondition {
    private LocalDateTime startDt;
    private LocalDateTime endDt;
    private String inOutCd;
    private String inOutDtlCd;
}
