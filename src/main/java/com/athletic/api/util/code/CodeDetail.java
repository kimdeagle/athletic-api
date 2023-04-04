package com.athletic.api.util.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeDetail {
    DUES_IN_DUES("회비"),
    DUES_IN_INTEREST("이자"),
    DUES_OUT_RENT_FEE("구장대여료"),
    DUES_OUT_BEVERAGE("음료"),
    DUES_ETC("기타")
    ;

    private String displayName;

    public String getId() {
        return name();
    }
}