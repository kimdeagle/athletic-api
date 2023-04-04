package com.athletic.api.dues.service;

import com.athletic.api.dues.dto.DuesAmountInterface;
import com.athletic.api.dues.dto.DuesResponseDto;
import com.athletic.api.dues.repository.DuesRepository;
import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import com.athletic.api.util.code.CodeGroup;
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
}
