package com.athletic.api.utils.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public enum CodeGroup {
    APPROVE_STATUS("APPROVE_STATUS", "승인상태", Arrays.asList(CodeDetail.APPROVE_STATUS_WAIT, CodeDetail.APPROVE_STATUS_NORMAL, CodeDetail.APPROVE_STATUS_REJECT)),
    EMAIL_TEMPLATE("EMAIL_TEMPLATE", "이메일 템플릿", Arrays.asList(CodeDetail.EMAIL_TEMPLATE_REQUEST_JOIN, CodeDetail.EMAIL_TEMPLATE_RESET_PASSWORD)),
    IN_OUT_IN("IN", "입금", Arrays.asList(CodeDetail.IN_OUT_IN_DUES, CodeDetail.IN_OUT_IN_INTEREST, CodeDetail.IN_OUT_ETC)),
    IN_OUT_OUT("OUT", "출금", Arrays.asList(CodeDetail.IN_OUT_OUT_RENT_FEE, CodeDetail.IN_OUT_OUT_BEVERAGE, CodeDetail.IN_OUT_ETC)),
    IN_OUT_REST("REST", "잔액", Collections.EMPTY_LIST),
    BG_COLOR("BG_COLOR", "배경색", Arrays.asList(CodeDetail.BG_COLOR_RED, CodeDetail.BG_COLOR_ORANGE, CodeDetail.BG_COLOR_GREEN, CodeDetail.BG_COLOR_BLUE, CodeDetail.BG_COLOR_PINK, CodeDetail.BG_COLOR_PURPLE, CodeDetail.BG_COLOR_BROWN)),
    ;

    private String code;
    private String name;
    private List<CodeDetail> detailList;

    public String getId() {
        return name();
    }
}
