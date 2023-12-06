package com.athletic.api.security.constant;

public class AuthConstant {
    public static final String[] PERMIT_ALL_PATH_LIST = {"/auth/login", "/auth/join", "/auth/logout", "/auth/reset-password", "/auth/refresh", "/sample"};
    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final long ACCESS_EXPIRATION_MS = 1000 * 60 * 5; //5mins
    public static final long REFRESH_EXPIRATION_MS = 1000 * 60 * 60 * 24; //24hours
    public static final String REFRESH_COOKIE_NAME = "refresh-jwt";
    public static final String REFRESH_COOKIE_PATH = "/api/auth/refresh";
    public static final String ANONYMOUS_USER = "anonymousUser";
}
