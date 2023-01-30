package com.athletic.api.auth.controller;

import com.athletic.api.auth.dto.AdminRequestDto;
import com.athletic.api.auth.dto.AdminResponseDto;
import com.athletic.api.auth.dto.TokenDto;
import com.athletic.api.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody AdminRequestDto adminRequestDto) {
        return ResponseEntity.ok(authService.login(adminRequestDto));
    }

    @PostMapping("/join")
    public ResponseEntity<AdminResponseDto> join(@RequestBody AdminRequestDto adminRequestDto) {
        return ResponseEntity.ok(authService.join(adminRequestDto));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<AdminResponseDto> resetPassword(@RequestBody AdminRequestDto adminRequestDto) {
        return ResponseEntity.ok(authService.resetPassword(adminRequestDto));
    }

    @PostMapping("/re-issue/access")
    public ResponseEntity<TokenDto> reIssueAccessToken() {
        return ResponseEntity.ok(authService.reIssueAccessToken());
    }
}
