package com.athletic.api.dues.dto;

import com.athletic.api.common.dto.BaseResponseDto;
import com.athletic.api.dues.entity.Dues;
import com.athletic.api.util.code.CodeFinder;
import com.athletic.api.util.excel.ExcelColumn;
import com.athletic.api.util.excel.style.ExcelCellStyle;
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

    @ExcelColumn(headerName = "시작일자", sort = 0, width = 20, bodyStyle = ExcelCellStyle.CENTER_BODY)
    private LocalDateTime startDt;

    @ExcelColumn(headerName = "종료일자", sort = 1, width = 20, bodyStyle = ExcelCellStyle.CENTER_BODY)
    private LocalDateTime endDt;

    @ExcelColumn(headerName = "회비명", sort = 4, bodyStyle = ExcelCellStyle.CENTER_BODY)
    private String title;

    @ExcelColumn(headerName = "상세내용", sort = 6, bodyStyle = ExcelCellStyle.CENTER_BODY)
    private String description;

    @ExcelColumn(headerName = "입출금액", sort = 5)
    private Long amount;

    @ExcelColumn(headerName = "입출구분", sort = 2, bodyStyle = ExcelCellStyle.CENTER_BODY)
    private String inOutName;

    @ExcelColumn(headerName = "입출상세", sort = 3, bodyStyle = ExcelCellStyle.CENTER_BODY)
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
