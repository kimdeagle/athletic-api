package com.athletic.api.auth.service;

import com.athletic.api.auth.entity.Admin;
import com.athletic.api.auth.entity.Authority;
import com.athletic.api.auth.repository.AdminRepository;
import com.athletic.api.auth.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return adminRepository.findByLoginId(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(Admin admin) {
        String authNm = authRepository.findById(admin.getAuthNo())
                .map(Authority::getAuthNm)
                .orElseThrow(() -> new RuntimeException("권한이 없습니다."));

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authNm);

        return new User(
                admin.getAdminNo(),
                admin.getLoginPw(),
                Collections.singleton(grantedAuthority)
        );
    }

}
