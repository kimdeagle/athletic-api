package com.athletic.api.menu.dto;

import com.athletic.api.menu.entity.Menu;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponseDto {
    private String menuNo;
    private String menuNm;
    private String upMenuNo;
    private String menuUrl;
    private String iconNm;
    private Long menuLevel;
    private Long sortSeq;
    private String useYn;
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;

    public static MenuResponseDto of (Menu menu) {
        return MenuResponseDto.builder()
                .menuNo(menu.getMenuNo())
                .menuNm(menu.getMenuNm())
                .upMenuNo(menu.getUpMenuNo())
                .menuUrl(menu.getMenuUrl())
                .iconNm(menu.getIconNm())
                .menuLevel(menu.getMenuLevel())
                .sortSeq(menu.getSortSeq())
                .useYn(menu.getUseYn())
                .build();
    }
}
