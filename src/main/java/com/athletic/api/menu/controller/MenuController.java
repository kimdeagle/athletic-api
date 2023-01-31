package com.athletic.api.menu.controller;

import com.athletic.api.menu.dto.MenuResponseDto;
import com.athletic.api.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping("")
    public ResponseEntity<List<MenuResponseDto>> getMenuList() {
        //TODO 추후 권한번호로 메뉴 리스트 가져오기
        return ResponseEntity.ok(menuService.getMenuList());
    }
}
