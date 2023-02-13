package com.athletic.api.auth.service;

import com.athletic.api.auth.config.SecurityUtil;
import com.athletic.api.auth.dto.AdminRequestDto;
import com.athletic.api.auth.entity.Admin;
import com.athletic.api.auth.repository.AdminRepository;
import com.athletic.api.auth.util.CryptUtil;
import com.athletic.api.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final AdminRepository adminRepository;
    private final CryptUtil cryptUtil;

    public ResponseDto changePassword(AdminRequestDto adminRequestDto) {
        String adminNo = SecurityUtil.getCurrentAdminNo();
        Admin admin = adminRepository.findById(adminNo).orElseThrow(() -> new RuntimeException("일치하는 계정이 없습니다."));

        String prevLoginPw = adminRequestDto.getLoginPw();
        String encPrevLoginPw = admin.getLoginPw();
        if (!cryptUtil.passwordEncoder().matches(prevLoginPw, encPrevLoginPw)) throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");

        String nextLoginPw = adminRequestDto.getChangePw();
        if (cryptUtil.passwordEncoder().matches(nextLoginPw, prevLoginPw)) throw new RuntimeException("기존과 동일한 비밀번호로 변경할 수 없습니다.");

        admin.setLoginPw(cryptUtil.passwordEncoder().encode(nextLoginPw));
        admin.setModId(adminNo);
        admin.setModDt(new Date());
        adminRepository.save(admin);

        return ResponseDto.builder()
                .code(ResponseDto.SUCCESS)
                .message("비밀번호 변경을 완료하였습니다.")
                .build();
    }
}
