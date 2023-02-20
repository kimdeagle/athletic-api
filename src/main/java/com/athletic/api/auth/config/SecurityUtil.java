package com.athletic.api.auth.config;

import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    private SecurityUtil() {}
    public static String getCurrentAdminNo() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new CustomException(ErrorCode.SECURITY_CONTEXT_NOT_FOUND);
        }

        return authentication.getName();
    }
}
