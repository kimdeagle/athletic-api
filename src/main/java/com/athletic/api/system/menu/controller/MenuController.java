package com.athletic.api.system.menu.controller;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.system.menu.dto.MenuRequestDto;
import com.athletic.api.system.menu.service.MenuSelector;
import com.athletic.api.system.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/system/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuSelector menuSelector;
    private final MenuService menuService;

    @GetMapping("")
    public ResponseEntity<ResponseDto> getMenuList() {
        return ResponseEntity.ok(menuSelector.getMenuList());
    }

    @GetMapping("/use")
    public ResponseEntity<ResponseDto> getUseMenuList() {
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
