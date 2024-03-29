package com.athletic.api.system.menu.service;

import com.athletic.api.security.utils.SecurityUtils;
import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.system.menu.dto.MenuResponseDto;
import com.athletic.api.system.menu.repository.MenuRepository;
import com.athletic.api.utils.constant.Const;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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
                menuRepository.findAll(Sort.by(Sort.Order.asc("menuLevel"), Sort.Order.asc("sortSeq")))
                        .stream().map(MenuResponseDto::of)
                        .collect(Collectors.toList());

        return ResponseDto.success(list);
    }

    public ResponseDto getUseMenuList() {
        List<MenuResponseDto> list =
                menuRepository.findAllByAdminIdAndUseYn(SecurityUtils.getCurrentId(), Const.USE_YN_Y)
                        .stream().map(MenuResponseDto::of)
                        .collect(Collectors.toList());

        return ResponseDto.success(list);
    }

}
