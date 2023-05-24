package com.athletic.api.security.dto;

import com.athletic.api.system.admin.entity.Admin;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String id;
    private String loginId;
    private String loginPw;
    private String authorityId;

    public CustomUserDetails(String id, String loginId, String loginPw, String authorityId) {
        this.id = id;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.authorityId = authorityId;
    }

    public static CustomUserDetails build(Admin admin) {
        return new CustomUserDetails(
                admin.getId(),
                admin.getLoginId(),
                admin.getLoginPw(),
                admin.getAuthorityId()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(authorityId));
    }

    @Override
    public String getPassword() {
        return this.loginPw;
    }

    @Override
    public String getUsername() {
        return this.loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
