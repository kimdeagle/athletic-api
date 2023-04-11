package com.athletic.api.dues.repository;

import com.athletic.api.dues.dto.DuesResponseDto;
import com.athletic.api.dues.entity.Dues;
import com.athletic.api.util.excel.ExcelDownloadSearchCondition;

import java.util.List;

public interface DuesRepositoryCustom {
    List<Dues> findAllByExcelDownloadSearchCondition(ExcelDownloadSearchCondition condition);
    List<DuesResponseDto> findAmountThisMonth();
}
