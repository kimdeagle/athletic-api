package com.athletic.api.system.authority.service;

import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import com.athletic.api.system.authority.dto.AuthorityResponseDto;
import com.athletic.api.system.authority.repository.AuthorityRepository;
import com.athletic.api.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthoritySelector {
    private final AuthorityRepository authorityRepository;

    public ResponseDto getAuthorities() {
        List<AuthorityResponseDto> list = authorityRepository.findAll().stream().map(AuthorityResponseDto::of).collect(Collectors.toList());

        return ResponseDto.success(list);
    }

    public ResponseDto getAuthority(String id) {
        AuthorityResponseDto dto = authorityRepository.findById(id).map(AuthorityResponseDto::of).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_AUTHORITY));

        return ResponseDto.success(dto);
    }
}
