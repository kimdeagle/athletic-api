package com.athletic.api.menu.service;

import com.athletic.api.admin.entity.Admin;
import com.athletic.api.admin.repository.AdminRepository;
import com.athletic.api.auth.util.SecurityUtil;
import com.athletic.api.authority.entity.AuthorityMenu;
import com.athletic.api.authority.repository.AuthorityMenuRepository;
import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import com.athletic.api.menu.dto.MenuResponseDto;
import com.athletic.api.menu.repository.MenuRepository;
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
    private final AuthorityMenuRepository authorityMenuRepository;

    public List<MenuResponseDto> getMenuList() {
        return menuRepository.findAll()
                .stream()
                .map(MenuResponseDto::of)
                .collect(Collectors.toList());
    }

    public List<MenuResponseDto> getUseMenuList() {
        //TODO JPA 연관관계 찾아보고 수정할 것!
        String authNo = adminRepository.findById(SecurityUtil.getCurrentAdminNo()).map(Admin::getAuthNo).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND));
        List<String> menuNoList = authorityMenuRepository.findAllByAuthNo(authNo).stream().map(AuthorityMenu::getMenuNo).collect(Collectors.toList());
        return menuRepository.findByMenuNoInAndUseYn(menuNoList, Const.USE_YN_Y)
                .stream()
                .map(MenuResponseDto::of)
                .collect(Collectors.toList());
    }

}
