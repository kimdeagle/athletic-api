package com.athletic.api.system.menu.dto;

import com.athletic.api.security.utils.SecurityUtils;
import com.athletic.api.system.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequestDto {
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

    public Menu toMenu() {
        return Menu.builder()
                .name(name)
                .upMenuId(upMenuId)
                .upMenuName(upMenuName)
                .menuUrl(menuUrl)
                .iconNm(iconNm)
                .menuLevel(menuLevel)
                .sortSeq(sortSeq)
                .useYn(useYn)
                .authorities(authorities)
                .regId(SecurityUtils.getCurrentId())
                .regDt(LocalDateTime.now())
                .modId(SecurityUtils.getCurrentId())
                .modDt(LocalDateTime.now())
                .build();
    }

    public Menu toUpdateMenu() {
        return Menu.builder()
                .id(id)
                .name(name)
                .upMenuId(upMenuId)
                .upMenuName(upMenuName)
                .menuUrl(menuUrl)
                .iconNm(iconNm)
                .menuLevel(menuLevel)
                .sortSeq(sortSeq)
                .useYn(useYn)
                .authorities(authorities)
                .modId(SecurityUtils.getCurrentId())
                .modDt(LocalDateTime.now())
                .build();
    }
}
