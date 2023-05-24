package com.athletic.api.security.service;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.common.message.SuccessMessage;
import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import com.athletic.api.security.constant.AuthConstant;
import com.athletic.api.security.dto.CustomUserDetails;
import com.athletic.api.security.dto.LoginRequestDto;
import com.athletic.api.security.utils.JwtUtils;
import com.athletic.api.security.utils.SecurityUtils;
import com.athletic.api.system.admin.dto.AdminRequestDto;
import com.athletic.api.system.admin.dto.AdminResponseDto;
import com.athletic.api.system.admin.entity.Admin;
import com.athletic.api.system.admin.repository.AdminRepository;
import com.athletic.api.system.menu.repository.MenuRepository;
import com.athletic.api.utils.code.CodeDetail;
import com.athletic.api.utils.constant.Const;
import com.athletic.api.utils.email.dto.EmailDto;
import com.athletic.api.utils.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtils jwtUtils;
    private final EmailService emailService;
    private final AdminRepository adminRepository;
    private final MenuRepository menuRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    public ResponseEntity<ResponseDto> login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getLoginId(), loginRequestDto.getLoginPw()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String accessJwt = jwtUtils.generateJwt(userDetails, AuthConstant.ACCESS_EXPIRATION_MS);

        String refreshJwt = jwtUtils.generateJwt(userDetails, AuthConstant.REFRESH_EXPIRATION_MS);

        ResponseCookie refreshCookie = jwtUtils.generateRefreshCookie(refreshJwt);

        Map<String, Object> body = new HashMap<>();
        body.put("accessJwt", accessJwt);
        body.put("user", getCurrentUserById());
        body.put("useMenuList", menuRepository.findAllByAdminIdAndUseYn(userDetails.getId(), Const.USE_YN_Y));

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(ResponseDto.success(SuccessMessage.Auth.LOGIN, body));

    }

    public ResponseEntity<ResponseDto> logout() {
        SecurityContextHolder.clearContext();
        ResponseCookie refreshCookie = jwtUtils.generateRefreshCookie(null);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(ResponseDto.success(SuccessMessage.Auth.LOGOUT));
    }

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

    public ResponseEntity<ResponseDto> refresh(HttpServletRequest request) {
        String refreshJwt = jwtUtils.getRefreshJwtFromCookie(request);

        if (StringUtils.isBlank(refreshJwt)) {
            throw new CustomException(ErrorCode.NOT_FOUND_REFRESH_JWT);
        }

        if (!jwtUtils.isValidJwt(refreshJwt)) {
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        }

        String loginId = jwtUtils.getLoginIdFromJwt(refreshJwt);

        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(loginId);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessJwt = jwtUtils.generateJwt(userDetails, AuthConstant.ACCESS_EXPIRATION_MS);

        Map<String, Object> body = new HashMap<>();
        body.put("accessJwt", accessJwt);
        body.put("user", getCurrentUserById());
        body.put("useMenuList", menuRepository.findAllByAdminIdAndUseYn(userDetails.getId(), Const.USE_YN_Y));

        return ResponseEntity.ok(ResponseDto.success(SuccessMessage.Auth.REFRESH, body));

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

    public ResponseDto getCurrentUser() {
        return ResponseDto.success(getCurrentUserById());
    }

    private AdminResponseDto getCurrentUserById() {
        return adminRepository.getCurrentUserById(SecurityUtils.getCurrentId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ADMIN));
    }
}
