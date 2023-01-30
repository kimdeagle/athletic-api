package com.athletic.api.auth.service;

import com.athletic.api.auth.dto.AdminRequestDto;
import com.athletic.api.auth.dto.AdminResponseDto;
import com.athletic.api.auth.dto.TokenDto;
import com.athletic.api.auth.entity.Admin;
import com.athletic.api.auth.jwt.TokenProvider;
import com.athletic.api.auth.repository.AdminRepository;
import com.athletic.api.auth.util.CryptUtil;
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
    private final CryptUtil cryptUtil;
    private final EmailService emailService;

    public AdminResponseDto join(AdminRequestDto adminRequestDto) {
        if (adminRepository.existsByLoginId(adminRequestDto.getLoginId())) {
            throw new RuntimeException("이미 가입되어 있습니다.");
        }

        setDefaultJoinAdminRequestDto(adminRequestDto);

        Admin admin = adminRequestDto.toAdmin();
        AdminResponseDto resultDto = AdminResponseDto.of(adminRepository.save(admin));

        sendJoinEmail(admin);

        return resultDto;
    }

    private void setDefaultJoinAdminRequestDto(AdminRequestDto adminRequestDto) {
        adminRequestDto.setRegId(Const.DEFAULT_ADMIN_ID);
        adminRequestDto.setRegDt(new Date());
        adminRequestDto.setModId(Const.DEFAULT_ADMIN_ID);
        adminRequestDto.setModDt(new Date());
        adminRequestDto.setAuthNo(Const.AUTH_NO_MANAGER);
        adminRequestDto.setAprvStCd(Const.APRV_ST_CD_WAIT);
        //encrypt
        adminRequestDto.setEmail(cryptUtil.encryptAES256(adminRequestDto.getEmail()));
        adminRequestDto.setMobileNo(cryptUtil.encryptAES256(adminRequestDto.getMobileNo().replaceAll("[^0-9]", "")));
        adminRequestDto.setLoginPw(cryptUtil.passwordEncoder().encode(adminRequestDto.getLoginPw()));
    }

    private void sendJoinEmail(Admin admin) {
        EmailDto emailDto = new EmailDto();

        emailDto.setTo(cryptUtil.decryptAES256(admin.getEmail()));
        emailDto.setTemplateCd(Const.EMAIL_TEMPLATE_CD_JOIN);

        Map<String, String> templateMap = new HashMap<>();
        templateMap.put("adminNm", admin.getAdminNm());
        emailDto.setTemplateMap(templateMap);

        emailService.sendEmail(emailDto);
    }

    public TokenDto login(AdminRequestDto adminRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = adminRequestDto.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        AdminResponseDto adminResponseDto = adminRepository.findById(authentication.getName()).map(AdminResponseDto::of).orElseThrow(() -> new RuntimeException("일치하는 계정이 없습니다."));
        if (StringUtils.equals(adminResponseDto.getAprvStCd(), Const.APRV_ST_CD_WAIT)) throw new RuntimeException("가입대기 상태의 계정입니다.");
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + Const.ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiresIn = new Date(now + Const.REFRESH_TOKEN_EXPIRE_TIME);
        String adminNm = adminResponseDto.getAdminNm();
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

    public AdminResponseDto resetPassword(AdminRequestDto adminRequestDto) {
        adminRequestDto.setEmail(cryptUtil.encryptAES256(adminRequestDto.getEmail()));

        Admin admin = adminRepository.findByLoginIdAndEmail(adminRequestDto.getLoginId(), adminRequestDto.getEmail()).orElseThrow(() -> new RuntimeException("일치하는 계정이 없습니다."));

        String tempPassword = getTempPassword();
        admin.setLoginPw(cryptUtil.passwordEncoder().encode(tempPassword));
        admin.setModId(admin.getAdminNo());
        admin.setModDt(new Date());

        AdminResponseDto responseDto = AdminResponseDto.of(adminRepository.save(admin));

        sendTempPasswordEmail(adminRequestDto, tempPassword);

        return responseDto;
    }

    private String getTempPassword() {
        String[] specialCharacter = "~,!,@,#,$,%,^,&,*,(,),_,+".split(",");
        Random rnd = new Random();
        String randomSpecialCharacter = specialCharacter[rnd.nextInt(specialCharacter.length)];
        return RandomStringUtils.randomAlphanumeric(8) + randomSpecialCharacter;
    }

    private void sendTempPasswordEmail(AdminRequestDto adminRequestDto, String tempPassword) {
        EmailDto emailDto = new EmailDto();

        emailDto.setTo(cryptUtil.decryptAES256(adminRequestDto.getEmail()));
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
        String adminNm = adminRepository.findById(authentication.getName()).map(Admin::getAdminNm).orElseThrow(() -> new RuntimeException("일치하는 회원이 없습니다."));
        String accessToken = tokenProvider.createToken(authentication, adminNm, accessTokenExpiresIn);
        return TokenDto.builder()
                .grantType(Const.BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .build();
    }

}
