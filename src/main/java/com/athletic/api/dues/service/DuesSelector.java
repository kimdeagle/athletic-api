package com.athletic.api.dues.service;

import com.athletic.api.dues.dto.DuesAmountInterface;
import com.athletic.api.dues.dto.DuesResponseDto;
import com.athletic.api.dues.repository.DuesRepository;
import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import com.athletic.api.util.code.CodeGroup;
import com.athletic.api.util.excel.ExcelDownloadSearchCondition;
import com.athletic.api.util.excel.ExcelWriter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DuesSelector {
    private final DuesRepository duesRepository;

    public List<DuesResponseDto> getDuesList() {
        return duesRepository.findAll().stream().map(DuesResponseDto::of).collect(Collectors.toList());
    }

    //TODO exception 수정
    public DuesResponseDto getDues(String id) {
        return duesRepository.findById(id).map(DuesResponseDto::of).orElseThrow(() -> new CustomException(ErrorCode.UNKNOWN));
    }

    public List<DuesAmountInterface> getAmountThisMonth() {
        return duesRepository.selectAmountThisMonth(CodeGroup.IN_OUT_IN.getCode(), CodeGroup.IN_OUT_OUT.getCode(), CodeGroup.IN_OUT_REST.getCode());
    }

    public void downloadExcel(ExcelDownloadSearchCondition search) {
        List<DuesResponseDto> list =
                ObjectUtils.isEmpty(search.getStartDt())
                        ? duesRepository.findAll(Sort.by("startDt", "endDt", "amount")).stream().map(DuesResponseDto::of).collect(Collectors.toList())
                        : duesRepository.findByPeriod(search.getStartDt(), search.getEndDt()).stream().map(DuesResponseDto::of).collect(Collectors.toList());
        ExcelWriter.write("회비 내역", list, DuesResponseDto.class);
    }
}
