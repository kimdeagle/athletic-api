package com.athletic.api.util.excel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExcelDownloadSearchCondition {
    private String period;
    private LocalDateTime startDt;
    private LocalDateTime endDt;
}