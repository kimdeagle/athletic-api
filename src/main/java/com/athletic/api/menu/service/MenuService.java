package com.athletic.api.menu.service;

import com.athletic.api.auth.config.SecurityUtil;
import com.athletic.api.auth.dto.AdminResponseDto;
import com.athletic.api.menu.dto.MenuResponseDto;
import com.athletic.api.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public List<MenuResponseDto> getMenuList() {
        return menuRepository.findAll()
                .stream()
                .map(MenuResponseDto::of)
                .collect(Collectors.toList());
    }

}
