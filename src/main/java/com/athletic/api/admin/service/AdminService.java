package com.athletic.api.admin.service;

import com.athletic.api.auth.util.SecurityUtil;
import com.athletic.api.admin.dto.AdminRequestDto;
import com.athletic.api.admin.entity.Admin;
import com.athletic.api.admin.repository.AdminRepository;
import com.athletic.api.common.dto.ResponseDto;
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
        String adminNo = SecurityUtil.getCurrentAdminNo();

        Admin admin = adminRepository.findById(adminNo).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND));

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

        return ResponseDto.builder()
                .code(ResponseDto.SUCCESS)
                .message("비밀번호 변경을 완료하였습니다.")
                .build();
    }

    public ResponseDto out(AdminRequestDto adminRequestDto) {
        String adminNo = SecurityUtil.getCurrentAdminNo();
        Admin admin = adminRepository.findById(adminNo).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND));
        String encLoginPw = admin.getLoginPw();
        if (!passwordEncoder.matches(adminRequestDto.getLoginPw(), encLoginPw))
            throw new CustomException(ErrorCode.NOT_MATCH_CURRENT_PASSWORD);

        adminRepository.deleteById(adminNo);

        return ResponseDto.builder()
                .code(ResponseDto.SUCCESS)
                .message("계정이 삭제되었습니다.")
                .build();
    }
}
