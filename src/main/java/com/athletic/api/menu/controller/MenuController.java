package com.athletic.api.menu.controller;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.menu.dto.MenuRequestDto;
import com.athletic.api.menu.dto.MenuResponseDto;
import com.athletic.api.menu.service.MenuSelector;
import com.athletic.api.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuSelector menuSelector;
    private final MenuService menuService;

    @GetMapping("")
    public ResponseEntity<List<MenuResponseDto>> getMenuList() {
        return ResponseEntity.ok(menuSelector.getMenuList());
    }

    @GetMapping("/use")
    public ResponseEntity<List<MenuResponseDto>> getUseMenuList() {
        return ResponseEntity.ok(menuSelector.getUseMenuList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteMenu(@PathVariable("id") String id) {
        return ResponseEntity.ok(menuService.deleteMenu(id));
    }

    @PostMapping
    public ResponseEntity<ResponseDto> saveMenu(@RequestBody MenuRequestDto dto) {
        return ResponseEntity.ok(menuService.saveMenu(dto));
    }
}
