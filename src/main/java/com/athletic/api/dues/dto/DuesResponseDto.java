package com.athletic.api.dues.dto;

import com.athletic.api.common.dto.BaseResponseDto;
import com.athletic.api.dues.entity.Dues;
import com.athletic.api.utils.code.CodeFinder;
import com.athletic.api.utils.excel.ExcelDownloadColumn;
import com.athletic.api.utils.excel.style.ExcelCellStyle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DuesResponseDto extends BaseResponseDto {
    private String id;

    private String inOutCd;

    private String inOutDtlCd;

    @ExcelDownloadColumn(headerName = "시작일자", sort = 0, width = 20, bodyStyle = ExcelCellStyle.CENTER_BODY)
    private LocalDate startDt;

    @ExcelDownloadColumn(headerName = "종료일자", sort = 1, width = 20, bodyStyle = ExcelCellStyle.CENTER_BODY)
    private LocalDate endDt;

    @ExcelDownloadColumn(headerName = "회비명", sort = 4, width = 20)
    private String title;

    @ExcelDownloadColumn(headerName = "상세내용", sort = 6, width = 30)
    private String description;

    @ExcelDownloadColumn(headerName = "입출금액", sort = 5)
    private Long amount;

    @ExcelDownloadColumn(headerName = "입출구분", sort = 2, bodyStyle = ExcelCellStyle.CENTER_BODY)
    private String inOutName;

    @ExcelDownloadColumn(headerName = "입출상세", sort = 3, bodyStyle = ExcelCellStyle.CENTER_BODY)
    private String inOutDtlName;

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
                .inOutName(CodeFinder.findNameByGroupCode(dues.getInOutCd()))
                .inOutDtlName(CodeFinder.findNameByDetailCode(dues.getInOutDtlCd()))
                .regId(dues.getRegId())
                .regDt(dues.getRegDt())
                .modId(dues.getModId())
                .modDt(dues.getModDt())
                .build();
    }

}
