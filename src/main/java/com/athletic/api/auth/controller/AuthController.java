package com.athletic.api.auth.controller;

import com.athletic.api.admin.dto.AdminRequestDto;
import com.athletic.api.auth.dto.TokenDto;
import com.athletic.api.auth.service.AuthService;
import com.athletic.api.common.dto.ResponseDto;
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
    public ResponseEntity<ResponseDto> join(@RequestBody AdminRequestDto adminRequestDto) {
        return ResponseEntity.ok(authService.join(adminRequestDto));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseDto> resetPassword(@RequestBody AdminRequestDto adminRequestDto) {
        return ResponseEntity.ok(authService.resetPassword(adminRequestDto));
    }

    @PostMapping("/re-issue/access")
    public ResponseEntity<TokenDto> reIssueAccessToken() {
        return ResponseEntity.ok(authService.reIssueAccessToken());
    }

    @PostMapping("/out")
    public ResponseEntity<ResponseDto> out(@RequestBody AdminRequestDto adminRequestDto) { return ResponseEntity.ok(authService.out(adminRequestDto)); }
}
