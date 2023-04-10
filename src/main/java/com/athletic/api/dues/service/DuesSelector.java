package com.athletic.api.dues.service;

import com.athletic.api.common.dto.ResponseDto;
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

    public ResponseDto getDuesList() {
        List<DuesResponseDto> list = duesRepository.findAll().stream().map(DuesResponseDto::of).collect(Collectors.toList());

        return ResponseDto.success(list);
    }

    public ResponseDto getDues(String id) {
        DuesResponseDto dto = duesRepository.findById(id).map(DuesResponseDto::of).orElseThrow(() -> new CustomException(ErrorCode.UNKNOWN));

        return ResponseDto.success(dto);
    }

    public ResponseDto getAmountThisMonth() {
        List<DuesAmountInterface> list = duesRepository.selectAmountThisMonth(CodeGroup.IN_OUT_IN.getCode(), CodeGroup.IN_OUT_OUT.getCode(), CodeGroup.IN_OUT_REST.getCode());

        return ResponseDto.success(list);
    }

    public void downloadExcel(ExcelDownloadSearchCondition search) {
        List<DuesResponseDto> list =
                ObjectUtils.isEmpty(search.getStartDt())
                        ? duesRepository.findAll(Sort.by("startDt", "endDt", "amount")).stream().map(DuesResponseDto::of).collect(Collectors.toList())
                        : duesRepository.findByPeriod(search.getStartDt(), search.getEndDt()).stream().map(DuesResponseDto::of).collect(Collectors.toList());

        ExcelWriter.write("회비 내역", list, DuesResponseDto.class);
    }
}
