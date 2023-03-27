package com.athletic.api.dues.service;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.dues.dto.DuesRequestDto;
import com.athletic.api.dues.repository.DuesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DuesService {
    private final DuesRepository duesRepository;

    public ResponseDto deleteDues(String id) {
        duesRepository.deleteById(id);
        return ResponseDto.builder()
                .code(ResponseDto.SUCCESS)
                .message("회비 내역이 삭제되었습니다.")
                .build();
    }

    public ResponseDto addDues(DuesRequestDto duesRequestDto) {
        duesRepository.save(duesRequestDto.toDues());
        return ResponseDto.builder()
                .code(ResponseDto.SUCCESS)
                .message("회비가 추가되었습니다.")
                .build();
    }

    public ResponseDto updateDues(DuesRequestDto duesRequestDto) {
        duesRepository.save(duesRequestDto.toUpdateDues());
        return ResponseDto.builder()
                .code(ResponseDto.SUCCESS)
                .message("회비가 수정되었습니다.")
                .build();
    }
}
