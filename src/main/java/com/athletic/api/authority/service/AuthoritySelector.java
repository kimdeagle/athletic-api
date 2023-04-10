package com.athletic.api.authority.service;

import com.athletic.api.authority.dto.AuthorityResponseDto;
import com.athletic.api.authority.repository.AuthorityRepository;
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

}
