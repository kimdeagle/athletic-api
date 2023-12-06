package com.athletic.api.security.utils;

import com.athletic.api.security.constant.AuthConstant;
import com.athletic.api.security.dto.CustomUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {
    private final Key key;

    public JwtUtils(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String getJwtFromHeader(HttpServletRequest request) {
        String header = request.getHeader(AuthConstant.AUTHORIZATION_HEADER_NAME);
        if (StringUtils.hasText(header) && header.startsWith(AuthConstant.BEARER_PREFIX)) {
            return header.substring(AuthConstant.BEARER_PREFIX.length());
        }
        return null;
    }

    public boolean isValidJwt(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String getLoginIdFromJwt(String jwt) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody().getSubject();
    }

    public String generateJwt(CustomUserDetails userDetails, long expirationMs) {
        return Jwts.builder()
                .setId(userDetails.getId())
                .setSubject(userDetails.getLoginId())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public ResponseCookie generateRefreshCookie(@Nullable String jwt) {
        return ResponseCookie.from(AuthConstant.REFRESH_COOKIE_NAME, jwt).path(AuthConstant.REFRESH_COOKIE_PATH).maxAge(AuthConstant.REFRESH_EXPIRATION_MS).httpOnly(true).build();
    }

    public String getRefreshJwtFromCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, AuthConstant.REFRESH_COOKIE_NAME);
        return cookie != null ? cookie.getValue() : null;
    }
}
