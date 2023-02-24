package com.athletic.api.menu.dto;

import com.athletic.api.auth.util.SecurityUtil;
import com.athletic.api.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequestDto {
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

    public Menu toMenu() {
        return Menu.builder()
                .menuNo(menuNo)
                .menuNm(menuNm)
                .upMenuNo(upMenuNo)
                .menuUrl(menuUrl)
                .iconNm(iconNm)
                .menuLevel(menuLevel)
                .sortSeq(sortSeq)
                .useYn(useYn)
                .regId(SecurityUtil.getCurrentAdminNo())
                .regDt(LocalDateTime.now())
                .modId(SecurityUtil.getCurrentAdminNo())
                .modDt(LocalDateTime.now())
                .build();
    }

    public Menu toUpdateMenu() {
        return Menu.builder()
                .menuNo(menuNo)
                .menuNm(menuNm)
                .upMenuNo(upMenuNo)
                .menuUrl(menuUrl)
                .iconNm(iconNm)
                .menuLevel(menuLevel)
                .sortSeq(sortSeq)
                .useYn(useYn)
                .modId(SecurityUtil.getCurrentAdminNo())
                .modDt(LocalDateTime.now())
                .build();
    }
}
