package com.athletic.api.auth.controller;

import com.athletic.api.auth.dto.AdminRequestDto;
import com.athletic.api.auth.dto.AdminResponseDto;
import com.athletic.api.auth.service.AdminSelector;
import com.athletic.api.auth.service.AdminService;
import com.athletic.api.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminSelector adminSelector;
    private final AdminService adminService;

    @GetMapping("/my")
    public ResponseEntity<AdminResponseDto> getMyInfo() {
        return ResponseEntity.ok(adminSelector.getInfoBySecurity());
    }

    @PutMapping("/password")
    public ResponseEntity<ResponseDto> changePassword(@RequestBody AdminRequestDto adminRequestDto) {
        return ResponseEntity.ok(adminService.changePassword(adminRequestDto));
    }

}
