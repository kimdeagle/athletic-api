package com.athletic.api.system.menu.service;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.common.message.SuccessMessage;
import com.athletic.api.system.menu.dto.MenuRequestDto;
import com.athletic.api.system.menu.entity.Menu;
import com.athletic.api.system.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {
    private final MenuRepository menuRepository;

    public ResponseDto deleteMenu(String id) {
        List<String> childIdList = menuRepository.findAllByUpMenuId(id).stream().map(Menu::getId).collect(Collectors.toList());
        //delete children
        menuRepository.deleteAllByIdInBatch(childIdList);
        //delete menu
        menuRepository.deleteById(id);

        return ResponseDto.success(SuccessMessage.Menu.DELETE_MENU);
    }

    public ResponseDto addMenu(MenuRequestDto menuRequestDto) {
        menuRepository.save(menuRequestDto.toMenu());

        return ResponseDto.success(SuccessMessage.Menu.ADD_MENU);
    }

    public ResponseDto updateMenu(MenuRequestDto menuRequestDto) {
        //update menu
        menuRepository.save(menuRequestDto.toUpdateMenu());
        //update children
        List<Menu> children = menuRepository.findAllByUpMenuId(menuRequestDto.getId());
        if (children.size() > 0) {
            children.forEach(child -> {
                child.setUpMenuName(menuRequestDto.getName());
                child.setModColumnsDefaultValue();
            });
            menuRepository.saveAll(children);
        }
        return ResponseDto.success(SuccessMessage.Menu.UPDATE_MENU);
    }

}