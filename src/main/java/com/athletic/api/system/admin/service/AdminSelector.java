package com.athletic.api.system.admin.service;

import com.athletic.api.auth.util.SecurityUtil;
import com.athletic.api.system.admin.dto.AdminResponseDto;
import com.athletic.api.system.admin.repository.AdminRepository;
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

    public ResponseDto getCurrentUser() {
        AdminResponseDto dto = adminRepository.getCurrentUserById(SecurityUtil.getCurrentId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ADMIN));

        return ResponseDto.success(dto);
    }

    public ResponseDto getAdminById(String id) {
        AdminResponseDto dto = adminRepository.findById(id).map(AdminResponseDto::of).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ADMIN));

        return ResponseDto.success(dto);
    }
}
