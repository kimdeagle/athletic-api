package com.athletic.api.auth.util;

import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public SecurityUtil() {}

    public static String getCurrentId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_AUTH_AT_SECURITY_CONTEXT);
        }

        return authentication.getName();
    }
}
