package com.athletic.api.util.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeDetail {
    EMAIL_TEMPLATE_REQUEST_JOIN("회원가입"),
    EMAIL_TEMPLATE_RESET_PASSWORD("비밀번호 초기화"),
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
