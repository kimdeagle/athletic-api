package com.athletic.api.auth.service;

import com.athletic.api.auth.util.PasswordUtils;
import com.athletic.api.system.admin.dto.AdminRequestDto;
import com.athletic.api.auth.dto.TokenDto;
import com.athletic.api.system.admin.entity.Admin;
import com.athletic.api.auth.jwt.TokenProvider;
import com.athletic.api.system.admin.repository.AdminRepository;
import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.common.message.SuccessMessage;
import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import com.athletic.api.utils.code.CodeDetail;
import com.athletic.api.utils.constant.Const;
import com.athletic.api.utils.email.dto.EmailDto;
import com.athletic.api.utils.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final PasswordUtils passwordUtils;
    private final EmailService emailService;

    public ResponseDto join(AdminRequestDto adminRequestDto) {
        if (adminRepository.existsByLoginId(adminRequestDto.getLoginId())) {
            throw new CustomException(ErrorCode.EXIST_LOGIN_ID);
        }

        Admin admin = adminRequestDto.toAdmin();

        adminRepository.save(admin);

        sendJoinEmail(admin);

        return ResponseDto.success(SuccessMessage.getMessageByParams(SuccessMessage.Auth.REQUEST_JOIN, admin.getName()));
    }

    private void sendJoinEmail(Admin admin) {
        EmailDto emailDto = new EmailDto();

        emailDto.setTo(admin.getEmail());
        emailDto.setCode(CodeDetail.EMAIL_TEMPLATE_REQUEST_JOIN.getCode());

        Map<String, String> templateMap = new HashMap<>();
        templateMap.put("adminNm", admin.getName());
        emailDto.setTemplateMap(templateMap);

        emailService.sendEmail(emailDto);
    }

    public ResponseDto login(AdminRequestDto adminRequestDto) {
        Admin admin = adminRepository.findByLoginId(adminRequestDto.getLoginId()).orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID_OR_PASSWORD));

        if (!passwordUtils.getPasswordEncoder().matches(adminRequestDto.getLoginPw(), admin.getLoginPw())) {
            throw new CustomException(ErrorCode.INVALID_ID_OR_PASSWORD);
        }
        if (StringUtils.equals(admin.getApproveStatusCd(), CodeDetail.APPROVE_STATUS_WAIT.getCode())) {
            throw new CustomException(ErrorCode.INVALID_APPROVE_STATUS, CodeDetail.APPROVE_STATUS_WAIT.getName());
        }
        if (StringUtils.equals(admin.getApproveStatusCd(), CodeDetail.APPROVE_STATUS_REJECT.getCode())) {
            throw new CustomException(ErrorCode.INVALID_APPROVE_STATUS, CodeDetail.APPROVE_STATUS_REJECT.getName());
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(adminRequestDto.getLoginId(), adminRequestDto.getLoginPw());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + Const.ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiresIn = new Date(now + Const.REFRESH_TOKEN_EXPIRE_TIME);

        String accessToken = tokenProvider.createToken(authentication, accessTokenExpiresIn);
        String refreshToken = tokenProvider.createToken(authentication, refreshTokenExpiresIn);

        TokenDto tokenDto = TokenDto.builder()
                .grantType(Const.BEARER_PREFIX)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .refreshTokenExpiresIn(refreshTokenExpiresIn.getTime())
                .build();

        return ResponseDto.success(tokenDto);

    }

    public ResponseDto resetPassword(AdminRequestDto adminRequestDto) {
        Admin admin = adminRepository.findByLoginIdAndEmail(adminRequestDto.getLoginId(), adminRequestDto.getEmail()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ADMIN));

        String tempPassword = getTempPassword();

        admin.setLoginPw(tempPassword);
        admin.setModColumnsDefaultValue();

        adminRepository.save(admin);

        sendTempPasswordEmail(adminRequestDto.getEmail(), tempPassword);

        return ResponseDto.success(SuccessMessage.Auth.RESET_PASSWORD);
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
        emailDto.setCode(CodeDetail.EMAIL_TEMPLATE_RESET_PASSWORD.getCode());

        Map<String, String> templateMap = new HashMap<>();
        templateMap.put("tempPassword", tempPassword);
        emailDto.setTemplateMap(templateMap);

        emailService.sendEmail(emailDto);
    }

    public ResponseDto reIssueAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + Const.ACCESS_TOKEN_EXPIRE_TIME);

        String accessToken = tokenProvider.createToken(authentication, accessTokenExpiresIn);

        TokenDto tokenDto = TokenDto.builder()
                .grantType(Const.BEARER_PREFIX)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .build();

        return ResponseDto.success(tokenDto);
    }

}
