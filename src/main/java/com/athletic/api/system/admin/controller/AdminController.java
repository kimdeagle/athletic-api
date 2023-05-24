package com.athletic.api.system.admin.controller;

import com.athletic.api.system.admin.dto.AdminRequestDto;
import com.athletic.api.system.admin.service.AdminSelector;
import com.athletic.api.system.admin.service.AdminService;
import com.athletic.api.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminSelector adminSelector;
    private final AdminService adminService;

    @PutMapping("/password")
    public ResponseEntity<ResponseDto> changePassword(@RequestBody AdminRequestDto adminRequestDto) {
        return ResponseEntity.ok(adminService.changePassword(adminRequestDto));
    }

    @PostMapping("/out")
    public ResponseEntity<ResponseDto> out(@RequestBody AdminRequestDto adminRequestDto) { return ResponseEntity.ok(adminService.out(adminRequestDto)); }

    @GetMapping
    public ResponseEntity<ResponseDto> getAdminList() {
        return ResponseEntity.ok(adminSelector.getAdminList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getAdminById(@PathVariable("id") String id) {
        return ResponseEntity.ok(adminSelector.getAdminById(id));
    }

}
