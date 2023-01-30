package com.athletic.api.auth.service;

import com.athletic.api.auth.config.SecurityUtil;
import com.athletic.api.auth.dto.AdminResponseDto;
import com.athletic.api.auth.repository.AdminRepository;
import com.athletic.api.auth.util.CryptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {
    private final AdminRepository adminRepository;
    private final CryptUtil cryptUtil;

    public AdminResponseDto getInfoBySecurity() {
        return adminRepository.findById(SecurityUtil.getCurrentAdminNo())
                .map(admin -> {
                    admin.setEmail(cryptUtil.decryptAES256(admin.getEmail()));
                    return admin;
                })
                .map(AdminResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
    }

}
