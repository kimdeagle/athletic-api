package com.athletic.api.system.menu.service;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.system.menu.dto.MenuResponseDto;
import com.athletic.api.system.menu.repository.MenuRepository;
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

    public ResponseDto getMenuList() {
        List<MenuResponseDto> list =
                menuRepository.findAll()
                        .stream().map(MenuResponseDto::of)
                        .collect(Collectors.toList());

        return ResponseDto.success(list);
    }

    public ResponseDto getUseMenuList() {
        List<MenuResponseDto> list =
                menuRepository.findAllByAuthorityIdAndUseYn()
                        .stream().map(MenuResponseDto::of)
                        .collect(Collectors.toList());

        return ResponseDto.success(list);
    }

}
