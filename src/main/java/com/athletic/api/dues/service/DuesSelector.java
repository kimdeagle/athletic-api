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
        /* TODO 동적 쿼리를 위해 Querydsl 사용
         * 1. 설정 - https://www.inflearn.com/chats/669477/querydsl-springboot-2-7%EC%9D%98-gradle-%EC%84%A4%EC%A0%95%EC%9D%84-%EA%B3%B5%EC%9C%A0%ED%95%A9%EB%8B%88%EB%8B%A4
         * 2. 사용 예제
         *  - https://jaehoney.tistory.com/185
         *  - https://velog.io/@kangsan/%EC%82%AC%EB%82%B4-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-Dynamic-Query-%EC%A0%81%EC%9A%A9%EA%B8%B0
         *  - https://jojoldu.tistory.com/394
         */

//        List<DuesResponseDto> list = duesRepository.findAllBySearchCondition(search).stream().map(DuesResponseDto::of).collect(Collectors.toList());

//        ExcelWriter.write("회비 내역", list, DuesResponseDto.class);
    }
}
