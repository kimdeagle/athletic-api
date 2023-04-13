package com.athletic.api.util.excel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExcelDownloadSearchCondition {
    private LocalDate startDt;
    private LocalDate endDt;
    private String inOutCd;
    private String inOutDtlCd;
}
