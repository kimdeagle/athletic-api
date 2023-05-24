package com.athletic.api.security.controller;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.security.dto.LoginRequestDto;
import com.athletic.api.security.service.AuthService;
import com.athletic.api.system.admin.dto.AdminRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }

    @PostMapping("/join")
    public ResponseEntity<ResponseDto> join(@RequestBody AdminRequestDto adminRequestDto) {
        return ResponseEntity.ok(authService.join(adminRequestDto));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseDto> resetPassword(@RequestBody AdminRequestDto adminRequestDto) {
        return ResponseEntity.ok(authService.resetPassword(adminRequestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logout() {
        return authService.logout();
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseDto> refresh(HttpServletRequest request) {
        return authService.refresh(request);
    }

    @GetMapping("/user/current")
    public ResponseEntity<ResponseDto> getCurrentUser() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }
}
