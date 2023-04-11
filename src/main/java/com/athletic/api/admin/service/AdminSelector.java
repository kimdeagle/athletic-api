package com.athletic.api.admin.service;

import com.athletic.api.auth.util.SecurityUtil;
import com.athletic.api.admin.dto.AdminResponseDto;
import com.athletic.api.admin.repository.AdminRepository;
import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminSelector {
    private final AdminRepository adminRepository;

    public ResponseDto getInfoBySecurity() {
        AdminResponseDto dto = adminRepository.findById(SecurityUtil.getCurrentId())
                .map(AdminResponseDto::of)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ADMIN));

        return ResponseDto.success(dto);
    }
}
