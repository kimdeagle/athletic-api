package com.athletic.api.admin.service;

import com.athletic.api.auth.util.SecurityUtil;
import com.athletic.api.admin.dto.AdminRequestDto;
import com.athletic.api.admin.entity.Admin;
import com.athletic.api.admin.repository.AdminRepository;
import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.common.message.SuccessMessage;
import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseDto changePassword(AdminRequestDto adminRequestDto) {
        String id = SecurityUtil.getCurrentId();

        Admin admin = adminRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ADMIN));

        String prevRawLoginPw = adminRequestDto.getLoginPw();
        String prevEncLoginPw = admin.getLoginPw();

        if (!passwordEncoder.matches(prevRawLoginPw, prevEncLoginPw))
            throw new CustomException(ErrorCode.NOT_MATCH_CURRENT_PASSWORD);

        String nextRawLoginPw = adminRequestDto.getChangePw();
        if (passwordEncoder.matches(nextRawLoginPw, prevEncLoginPw))
            throw new CustomException(ErrorCode.CANNOT_CHANGE_SAME_PASSWORD);

        admin.setLoginPw(nextRawLoginPw);
        admin.setModColumnsDefaultValue();

        adminRepository.save(admin);

        return ResponseDto.success(SuccessMessage.Admin.CHANGE_PASSWORD);
    }

    public ResponseDto out(AdminRequestDto adminRequestDto) {
        String id = SecurityUtil.getCurrentId();
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ADMIN));
        String encLoginPw = admin.getLoginPw();
        if (!passwordEncoder.matches(adminRequestDto.getLoginPw(), encLoginPw))
            throw new CustomException(ErrorCode.NOT_MATCH_CURRENT_PASSWORD);

        adminRepository.deleteById(id);

        return ResponseDto.success(SuccessMessage.Admin.DELETE_ADMIN);
    }
}
