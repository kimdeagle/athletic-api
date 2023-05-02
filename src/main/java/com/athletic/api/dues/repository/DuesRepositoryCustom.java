package com.athletic.api.dues.repository;

import com.athletic.api.dues.entity.Dues;
import com.athletic.api.utils.excel.ExcelDownloadSearchCondition;

import java.util.List;

public interface DuesRepositoryCustom {
    List<Dues> findAllByExcelDownloadSearchCondition(ExcelDownloadSearchCondition condition);
}
