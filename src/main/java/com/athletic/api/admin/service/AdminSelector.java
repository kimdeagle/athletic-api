package com.athletic.api.admin.service;

import com.athletic.api.auth.util.SecurityUtil;
import com.athletic.api.admin.dto.AdminResponseDto;
import com.athletic.api.admin.repository.AdminRepository;
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

    public AdminResponseDto getInfoBySecurity() {
        return adminRepository.findById(SecurityUtil.getCurrentAdminNo())
                .map(AdminResponseDto::of)
                .orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND));
    }
}
