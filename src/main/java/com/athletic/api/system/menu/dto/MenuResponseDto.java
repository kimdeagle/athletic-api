package com.athletic.api.system.menu.dto;

import com.athletic.api.common.dto.BaseResponseDto;
import com.athletic.api.system.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponseDto extends BaseResponseDto {
    private String id;
    private String name;
    private String upMenuId;
    private String upMenuName;
    private String menuUrl;
    private String iconNm;
    private Long menuLevel;
    private Long sortSeq;
    private String useYn;
    private List<String> authorities;

    public static MenuResponseDto of (Menu menu) {
        return MenuResponseDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .upMenuId(menu.getUpMenuId())
                .upMenuName(menu.getUpMenuName())
                .menuUrl(menu.getMenuUrl())
                .iconNm(menu.getIconNm())
                .menuLevel(menu.getMenuLevel())
                .sortSeq(menu.getSortSeq())
                .useYn(menu.getUseYn())
                .authorities(menu.getAuthorities())
                .regId(menu.getRegId())
                .regDt(menu.getRegDt())
                .modId(menu.getModId())
                .modDt(menu.getModDt())
                .build();
    }
}
