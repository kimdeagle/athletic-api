package com.athletic.api.authority.controller;

import com.athletic.api.authority.dto.AuthorityResponseDto;
import com.athletic.api.authority.service.AuthoritySelector;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authority")
@RequiredArgsConstructor
public class AuthorityController {
    private final AuthoritySelector authoritySelector;

    @GetMapping
    public ResponseEntity<List<AuthorityResponseDto>> getAuthorities() {
        return ResponseEntity.ok(authoritySelector.getAuthorities());
    }

}
