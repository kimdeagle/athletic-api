package com.athletic.api.dues.service;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.dues.dto.DuesResponseDto;
import com.athletic.api.dues.repository.DuesRepository;
import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import com.athletic.api.utils.excel.ExcelDownloadSearchCondition;
import com.athletic.api.utils.excel.ExcelWriter;
import lombok.RequiredArgsConstructor;
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
        List<DuesResponseDto> list =
                duesRepository.findAll()
                        .stream().map(DuesResponseDto::of)
                        .collect(Collectors.toList());

        return ResponseDto.success(list);
    }

    public ResponseDto getDues(String id) {
        DuesResponseDto dto =
                duesRepository.findById(id)
                        .map(DuesResponseDto::of)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_DUES));

        return ResponseDto.success(dto);
    }

    public void downloadExcel(ExcelDownloadSearchCondition condition) {
        List<DuesResponseDto> list =
                duesRepository.findAllByExcelDownloadSearchCondition(condition)
                        .stream().map(DuesResponseDto::of)
                        .collect(Collectors.toList());

        ExcelWriter.write("회비 내역", list, DuesResponseDto.class);
    }
}
