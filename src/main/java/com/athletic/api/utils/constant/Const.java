package com.athletic.api.utils.constant;

public class Const {
    //common code
    public static final String DEFAULT_LOCAL_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_LOCAL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_ADMIN_ID = "admin";
    public static final String AUTH_NO_MANAGER = "10001";
    public static final String USE_YN_Y = "Y";
    public static final String USE_YN_N = "N";

    public static final String EXCEL_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    //regex
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9+\\-\\\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    public static final String MOBILE_NO_REGEX = "^01[016789][0-9]{3,4}[0-9]{4}$";
    public static final String DATE_REGEX = "^(19[0-9][0-9]|20\\d{2})-?(0[0-9]|1[0-2])-?(0[1-9]|[1-2][0-9]|3[0-1])$";
    public static final String NUMBER_REGEX = "^[0-9]*$";
}
