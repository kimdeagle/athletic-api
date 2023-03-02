package com.athletic.api.auth.service;

import com.athletic.api.admin.dto.AdminRequestDto;
import com.athletic.api.auth.dto.TokenDto;
import com.athletic.api.admin.entity.Admin;
import com.athletic.api.auth.jwt.TokenProvider;
import com.athletic.api.admin.repository.AdminRepository;
import com.athletic.api.auth.util.SecurityUtil;
import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import com.athletic.api.util.constant.Const;
import com.athletic.api.util.email.dto.EmailDto;
import com.athletic.api.util.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AdminRepository adminRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public ResponseDto join(AdminRequestDto adminRequestDto) {
        if (adminRepository.existsByLoginId(adminRequestDto.getLoginId()))
            throw new CustomException(ErrorCode.EXIST_LOGIN_ID);

        Admin admin = adminRequestDto.toAdmin();

        adminRepository.save(admin);

        sendJoinEmail(admin);

        return ResponseDto.builder()
                .code(ResponseDto.SUCCESS)
                .message(admin.getAdminNm() + "님. 회원가입 요청이 완료되었습니다.")
                .build();
    }

    private void sendJoinEmail(Admin admin) {
        EmailDto emailDto = new EmailDto();

        emailDto.setTo(admin.getEmail());
        emailDto.setTemplateCd(Const.EMAIL_TEMPLATE_CD_JOIN);

        Map<String, String> templateMap = new HashMap<>();
        templateMap.put("adminNm", admin.getAdminNm());
        emailDto.setTemplateMap(templateMap);

        emailService.sendEmail(emailDto);
    }

    public TokenDto login(AdminRequestDto adminRequestDto) {
        Admin admin = adminRepository.findByLoginId(adminRequestDto.getLoginId()).orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID_OR_PASSWORD));
        if (!passwordEncoder.matches(adminRequestDto.getLoginPw(), admin.getLoginPw())) throw new CustomException(ErrorCode.INVALID_ID_OR_PASSWORD);
        if (StringUtils.equals(admin.getAprvStCd(), Const.APRV_ST_CD_WAIT)) throw new CustomException(ErrorCode.WAIT_ADMIN_STATUS);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(adminRequestDto.getLoginId(), adminRequestDto.getLoginPw());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + Const.ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiresIn = new Date(now + Const.REFRESH_TOKEN_EXPIRE_TIME);
        String adminNm = admin.getAdminNm();
        String accessToken = tokenProvider.createToken(authentication, adminNm, accessTokenExpiresIn);
        String refreshToken = tokenProvider.createToken(authentication, adminNm, refreshTokenExpiresIn);

        return TokenDto.builder()
                .grantType(Const.BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .refreshTokenExpiresIn(refreshTokenExpiresIn.getTime())
                .build();

    }

    public ResponseDto resetPassword(AdminRequestDto adminRequestDto) {
        Admin admin = adminRepository.findByLoginIdAndEmail(adminRequestDto.getLoginId(), adminRequestDto.getEmail()).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND));

        String tempPassword = getTempPassword();

        admin.setLoginPw(tempPassword);
        admin.setModId(admin.getAdminNo());
        admin.setModDt(LocalDateTime.now());

        adminRepository.save(admin);

        sendTempPasswordEmail(adminRequestDto.getEmail(), tempPassword);

        return ResponseDto.builder()
                .code(ResponseDto.SUCCESS)
                .message("임시 비밀번호를 이메일로 전송했습니다.\n확인 후 로그인 하세요.")
                .build();
    }

    private String getTempPassword() {
        String[] specialCharacter = "~,!,@,#,$,%,^,&,*,(,),_,+".split(",");
        Random rnd = new Random();
        String randomSpecialCharacter = specialCharacter[rnd.nextInt(specialCharacter.length)];
        return RandomStringUtils.randomAlphanumeric(8) + randomSpecialCharacter;
    }

    private void sendTempPasswordEmail(String email, String tempPassword) {
        EmailDto emailDto = new EmailDto();

        emailDto.setTo(email);
        emailDto.setTemplateCd(Const.EMAIL_TEMPLATE_CD_RESET_PASSWORD);

        Map<String, String> templateMap = new HashMap<>();
        templateMap.put("tempPassword", tempPassword);
        emailDto.setTemplateMap(templateMap);

        emailService.sendEmail(emailDto);
    }

    public TokenDto reIssueAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + Const.ACCESS_TOKEN_EXPIRE_TIME);
        String adminNm = adminRepository.findById(authentication.getName()).map(Admin::getAdminNm).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND));
        String accessToken = tokenProvider.createToken(authentication, adminNm, accessTokenExpiresIn);
        return TokenDto.builder()
                .grantType(Const.BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
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
                .message("회원탈퇴 처리되었습니다.")
                .build();
    }
}
