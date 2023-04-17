package com.athletic.api.util.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeDetail {
    APPROVE_STATUS_WAIT("WAIT", "대기"),
    APPROVE_STATUS_NORMAL("NORMAL", "정상"),
    APPROVE_STATUS_REJECT("REJECT", "거절"),
    EMAIL_TEMPLATE_REQUEST_JOIN("REQUEST_JOIN", "계정생성 요청"),
    EMAIL_TEMPLATE_RESET_PASSWORD("RESET_PASSWORD", "비밀번호 초기화"),
    IN_OUT_IN_DUES("DUES", "회비"),
    IN_OUT_IN_INTEREST("INTEREST", "이자"),
    IN_OUT_OUT_RENT_FEE("RENT_FEE", "구장대여료"),
    IN_OUT_OUT_BEVERAGE("BEVERAGE", "음료"),
    IN_OUT_ETC("ETC", "기타"),
    BG_COLOR_RED("RED", "#F44336"),
    BG_COLOR_ORANGE("ORANGE", "#FF9800"),
    BG_COLOR_GREEN("GREEN", "#4CAF50"),
    BG_COLOR_BLUE("BLUE", "#2196F3"),
    BG_COLOR_PINK("PINK", "#E91E63"),
    BG_COLOR_PURPLE("PURPLE", "#9C27B0"),
    BG_COLOR_BROWN("BROWN", "#795548"),
    ;

    private String code;
    private String name;

    public String getId() {
        return name();
    }
}
