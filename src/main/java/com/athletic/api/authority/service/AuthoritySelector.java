package com.athletic.api.authority.service;

import com.athletic.api.authority.dto.AuthorityMenuResponseDto;
import com.athletic.api.authority.dto.AuthorityResponseDto;
import com.athletic.api.authority.repository.AuthorityMenuRepository;
import com.athletic.api.authority.repository.AuthorityRepository;
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
    private final AuthorityMenuRepository authorityMenuRepository;

    public List<AuthorityResponseDto> getAuthorities() {
        return authorityRepository.findAll().stream().map(AuthorityResponseDto::of).collect(Collectors.toList());
    }

    public List<AuthorityMenuResponseDto> getAuthorityMenuList() {
        return authorityMenuRepository.findAll().stream().map(AuthorityMenuResponseDto::of).collect(Collectors.toList());
    }
}
