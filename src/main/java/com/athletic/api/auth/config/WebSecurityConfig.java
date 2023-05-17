package com.athletic.api.auth.config;

import com.athletic.api.auth.handler.CustomLogoutSuccessHandler;
import com.athletic.api.auth.jwt.JwtAccessDeniedHandler;
import com.athletic.api.auth.jwt.JwtAuthenticationEntryPoint;
import com.athletic.api.auth.jwt.JwtFilter;
import com.athletic.api.auth.jwt.TokenProvider;
import com.athletic.api.utils.constant.Const;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomLogoutSuccessHandler logoutSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .authorizeRequests()
                .antMatchers(Const.PERMIT_PATH_LIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl(Const.LOGOUT_URL)
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
