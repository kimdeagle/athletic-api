package com.athletic.api.auth.service;

import com.athletic.api.admin.entity.Admin;
import com.athletic.api.authority.entity.Authority;
import com.athletic.api.authority.repository.AuthorityRepository;
import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import com.athletic.api.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return adminRepository.findByLoginId(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID_OR_PASSWORD));
    }

    private UserDetails createUserDetails(Admin admin) {
        String authName = authorityRepository.findById(admin.getAuthorityId())
                .map(Authority::getName)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_AUTHORITY));

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authName);

        return new User(
                admin.getId(),
                admin.getLoginPw(),
                Collections.singleton(grantedAuthority)
        );
    }

}
