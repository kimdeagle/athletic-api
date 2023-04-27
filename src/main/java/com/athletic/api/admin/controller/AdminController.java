package com.athletic.api.admin.controller;

import com.athletic.api.admin.dto.AdminRequestDto;
import com.athletic.api.admin.service.AdminSelector;
import com.athletic.api.admin.service.AdminService;
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

    @GetMapping("/user")
    public ResponseEntity<ResponseDto> getUser() {
        return ResponseEntity.ok(adminSelector.getUser());
    }

    @GetMapping("/my")
    public ResponseEntity<ResponseDto> getMyInfo() {
        return ResponseEntity.ok(adminSelector.getInfoBySecurity());
    }

    @PutMapping("/password")
    public ResponseEntity<ResponseDto> changePassword(@RequestBody AdminRequestDto adminRequestDto) {
        return ResponseEntity.ok(adminService.changePassword(adminRequestDto));
    }

    @PostMapping("/out")
    public ResponseEntity<ResponseDto> out(@RequestBody AdminRequestDto adminRequestDto) { return ResponseEntity.ok(adminService.out(adminRequestDto)); }

}
