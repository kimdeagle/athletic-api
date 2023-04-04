package com.athletic.api.system.menu.service;

import com.athletic.api.admin.entity.Admin;
import com.athletic.api.admin.repository.AdminRepository;
import com.athletic.api.auth.util.SecurityUtil;
import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import com.athletic.api.system.menu.dto.MenuResponseDto;
import com.athletic.api.system.menu.repository.MenuRepository;
import com.athletic.api.util.constant.Const;
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
    private final AdminRepository adminRepository;

    public List<MenuResponseDto> getMenuList() {
        return menuRepository.findAll()
                .stream()
                .map(MenuResponseDto::of)
                .collect(Collectors.toList());
    }

    public List<MenuResponseDto> getUseMenuList() {
        String authorityId = adminRepository.findById(SecurityUtil.getCurrentId()).map(Admin::getAuthorityId).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND));
        return menuRepository.findByAuthorityIdAndUseYn(authorityId, Const.USE_YN_Y)
                .stream()
                .map(MenuResponseDto::of)
                .collect(Collectors.toList());
    }

}
