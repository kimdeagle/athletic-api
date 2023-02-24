package com.athletic.api.util.constant;

public class Const {
    //permit path url list
    public static final String[] PERMIT_PATH_LIST = {"/auth/login", "/auth/join", "/auth/reset-password", "/auth/re-issue/access"};

    //logout url
    public static final String LOGOUT_URL = "/auth/logout";

    //auth
    public static final String AUTHORITIES_KEY = "auth";
    public static final String BEARER_TYPE = "bearer";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24;

    //common code
    public static final String DEFAULT_ADMIN_ID = "admin";
    public static final String APRV_ST_CD_WAIT = "W";
    public static final String APRV_ST_CD_APPROVE = "A";
    public static final String APRV_ST_CD_REJECT = "R";
    public static final String AUTH_NO_MANAGER = "10001";
    public static final String EMAIL_TEMPLATE_CD_JOIN = "10";
    public static final String EMAIL_TEMPLATE_CD_RESET_PASSWORD = "20";
}
