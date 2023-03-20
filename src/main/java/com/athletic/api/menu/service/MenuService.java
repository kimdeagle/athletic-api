package com.athletic.api.menu.service;

import com.athletic.api.authority.dto.AuthorityMenuRequestDto;
import com.athletic.api.authority.repository.AuthorityMenuRepository;
import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.menu.dto.MenuRequestDto;
import com.athletic.api.menu.entity.Menu;
import com.athletic.api.menu.repository.MenuRepository;
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
    private final AuthorityMenuRepository authorityMenuRepository;

    public ResponseDto deleteMenu(String menuNo) {
        List<String> childMenuNoList = menuRepository.findAllByUpMenuNo(menuNo).stream().map(Menu::getMenuNo).collect(Collectors.toList());
        //delete children
        menuRepository.deleteAllByIdInBatch(childMenuNoList);
        //delete menu
        menuRepository.deleteById(menuNo);

        return ResponseDto.builder()
                .code(ResponseDto.SUCCESS)
                .message("삭제를 완료했습니다.")
                .build();
    }

    public ResponseDto saveMenu(MenuRequestDto dto) {
        if (StringUtils.isBlank(dto.getMenuNo())) {
            menuRepository.save(dto.toMenu());
        } else {
            //update menu
            menuRepository.save(dto.toUpdateMenu());
            //delete authority menu
            authorityMenuRepository.deleteAllByMenuNo(dto.getMenuNo());
            //save authority menu
            dto.getAuthNoList().forEach(authNo -> saveAuthorityMenu(dto.getMenuNo(), authNo));
            //update children
            List<Menu> children = menuRepository.findAllByUpMenuNo(dto.getMenuNo());
            if (children.size() > 0) {
                children.forEach(child -> {
                    child.setUpMenuNm(dto.getMenuNm());
                    child.setModColumnsDefaultValue();
                });
                menuRepository.saveAll(children);
            }
        }
        return ResponseDto.builder()
                .code(ResponseDto.SUCCESS)
                .message(StringUtils.isEmpty(dto.getMenuNo()) ? "등록이 완료되었습니다." : "수정이 완료되었습니다.")
                .build();
    }

    private void saveAuthorityMenu(String menuNo, String authNo) {
            authorityMenuRepository.save(
                    AuthorityMenuRequestDto.builder()
                            .menuNo(menuNo)
                            .authNo(authNo)
                            .build()
                            .toAuthorityMenu());
    }
}