package com.athletic.api.utils.util;

import org.apache.commons.lang3.StringUtils;

public class Util {
    public static String maskMobileNo(String mobileNo) {
        if (StringUtils.isNotBlank(mobileNo)) {
            String pMobileNo = mobileNo.replaceAll("-", "");
            return pMobileNo.substring(0, 3) + "-****-" + pMobileNo.substring(7);
        }
        return mobileNo;
    }

    public static String maskEmail(String email) {
        if (StringUtils.isNotBlank(email)) {
            int atIndex = email.lastIndexOf("@");
            return email.substring(0, atIndex).replaceAll(".", "*") + email.substring(atIndex);
        }
        return email;
    }
}
