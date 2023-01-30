package com.athletic.api.auth.controller;

import com.athletic.api.auth.dto.AdminResponseDto;
import com.athletic.api.auth.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/me")
    public ResponseEntity<AdminResponseDto> getMyInfo() {
        AdminResponseDto adminResponseDto = adminService.getInfoBySecurity();
        return ResponseEntity.ok(adminResponseDto);
    }

}
