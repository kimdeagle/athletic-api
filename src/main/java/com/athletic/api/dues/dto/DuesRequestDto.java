package com.athletic.api.dues.dto;

import com.athletic.api.auth.util.SecurityUtil;
import com.athletic.api.dues.entity.Dues;
import com.athletic.api.exception.ErrorMessage;
import com.athletic.api.util.code.CodeFinder;
import com.athletic.api.util.constant.Const;
import com.athletic.api.util.excel.ExcelUploadColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DuesRequestDto {
    private String id;

    private String inOutCd;

    private String inOutDtlCd;

    @ExcelUploadColumn(colIndex = 1, required = true)
    private LocalDate startDt;

    @ExcelUploadColumn(colIndex = 2, required = true)
    private LocalDate endDt;

    @ExcelUploadColumn(colIndex = 5, required = true)
    private String title;

    @ExcelUploadColumn(colIndex = 7)
    private String description;

    @ExcelUploadColumn(colIndex = 6, required = true, validationRegex = Const.NUMBER_REGEX, errorMessage = ErrorMessage.ExcelUpload.INVALID_NUMBER_FORMAT)
    private Long amount;

    @ExcelUploadColumn(colIndex = 3, required = true)
    private String inOutName;

    @ExcelUploadColumn(colIndex = 4, required = true)
    private String inOutDtlName;

    public Dues toDues() {
        return Dues.builder()
                .inOutCd(inOutCd)
                .inOutDtlCd(inOutDtlCd)
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
                .inOutCd(inOutCd)
                .inOutDtlCd(inOutDtlCd)
                .startDt(startDt)
                .endDt(endDt)
                .title(title)
                .description(description)
                .amount(amount)
                .modId(SecurityUtil.getCurrentId())
                .modDt(LocalDateTime.now())
                .build();
    }

    public Dues toDuesByExcel() {
        return Dues.builder()
                .inOutCd(CodeFinder.findCodeByGroupName(inOutName))
                .inOutDtlCd(CodeFinder.findCodeByDetailName(inOutDtlName))
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
}
