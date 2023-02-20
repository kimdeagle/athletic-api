package com.athletic.api.auth.service;

import com.athletic.api.auth.config.SecurityUtil;
import com.athletic.api.auth.dto.AdminResponseDto;
import com.athletic.api.auth.repository.AdminRepository;
import com.athletic.api.auth.util.CryptUtil;
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
    private final CryptUtil cryptUtil;

    public AdminResponseDto getInfoBySecurity() {
        return adminRepository.findById(SecurityUtil.getCurrentAdminNo())
                .map(admin -> {
                    admin.setEmail(cryptUtil.decryptAES256(admin.getEmail()));
                    admin.setMobileNo(cryptUtil.decryptAES256(admin.getMobileNo()));
                    return admin;
                })
                .map(AdminResponseDto::of)
                .orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND));
    }
}
