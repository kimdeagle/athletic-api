package com.athletic.api.menu.dto;

import com.athletic.api.menu.entity.Menu;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponseDto {
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
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;

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
                .build();
    }
}
