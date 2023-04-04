package com.athletic.api.util.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public enum CodeGroup {
    DUES_IN("입금", Arrays.asList(CodeDetail.DUES_IN_DUES, CodeDetail.DUES_IN_INTEREST, CodeDetail.DUES_ETC)),
    DUES_OUT("출금", Arrays.asList(CodeDetail.DUES_OUT_RENT_FEE, CodeDetail.DUES_OUT_BEVERAGE, CodeDetail.DUES_ETC)),
    DUES_REST("잔액", Collections.EMPTY_LIST)
    ;

    private String displayName;
    private List<CodeDetail> detailList;

    public String getId() {
        return name();
    }
}
