package com.athletic.api.menu.service;

import com.athletic.api.menu.dto.MenuResponseDto;
import com.athletic.api.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuSelector {
    private final MenuRepository menuRepository;

    public List<MenuResponseDto> getMenuList() {
        return menuRepository.findAll()
                .stream()
                .map(MenuResponseDto::of)
                .collect(Collectors.toList());
    }

}
