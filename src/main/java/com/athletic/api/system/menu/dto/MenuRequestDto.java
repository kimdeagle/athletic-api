package com.athletic.api.system.menu.dto;

import com.athletic.api.auth.util.SecurityUtil;
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
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;

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
                .regId(SecurityUtil.getCurrentId())
                .regDt(LocalDateTime.now())
                .modId(SecurityUtil.getCurrentId())
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
                .modId(SecurityUtil.getCurrentId())
                .modDt(LocalDateTime.now())
                .build();
    }
}
