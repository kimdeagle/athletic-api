package com.athletic.api.system.menu.service;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.system.menu.dto.MenuRequestDto;
import com.athletic.api.system.menu.entity.Menu;
import com.athletic.api.system.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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

        return ResponseDto.builder()
                .code(ResponseDto.SUCCESS)
                .message("삭제를 완료했습니다.")
                .build();
    }

    public ResponseDto saveMenu(MenuRequestDto dto) {
        if (StringUtils.isBlank(dto.getId())) {
            menuRepository.save(dto.toMenu());
        } else {
            //update menu
            menuRepository.save(dto.toUpdateMenu());
            //update children
            List<Menu> children = menuRepository.findAllByUpMenuId(dto.getId());
            if (children.size() > 0) {
                children.forEach(child -> {
                    child.setUpMenuName(dto.getName());
                    child.setModColumnsDefaultValue();
                });
                menuRepository.saveAll(children);
            }
        }
        return ResponseDto.builder()
                .code(ResponseDto.SUCCESS)
                .message(StringUtils.isEmpty(dto.getId()) ? "등록이 완료되었습니다." : "수정이 완료되었습니다.")
                .build();
    }

}