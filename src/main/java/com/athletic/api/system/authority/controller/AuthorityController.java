package com.athletic.api.system.authority.controller;

import com.athletic.api.system.authority.dto.AuthorityRequestDto;
import com.athletic.api.system.authority.service.AuthoritySelector;
import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.system.authority.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/system/authority")
@RequiredArgsConstructor
public class AuthorityController {
    private final AuthoritySelector authoritySelector;
    private final AuthorityService authorityService;

    @GetMapping
    public ResponseEntity<ResponseDto> getAuthorities() {
        return ResponseEntity.ok(authoritySelector.getAuthorities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getAuthority(@PathVariable("id") String id) {
        return ResponseEntity.ok(authoritySelector.getAuthority(id));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteAuthorities(@RequestBody List<String> idList) {
        return ResponseEntity.ok(authorityService.deleteAuthorities(idList));
    }

    @PostMapping
    public ResponseEntity<ResponseDto> addAuthority(@RequestBody AuthorityRequestDto authorityRequestDto) {
        return ResponseEntity.ok(authorityService.addAuthority(authorityRequestDto));
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateAuthority(@RequestBody AuthorityRequestDto authorityRequestDto) {
        return ResponseEntity.ok(authorityService.updateAuthority(authorityRequestDto));
    }

}
