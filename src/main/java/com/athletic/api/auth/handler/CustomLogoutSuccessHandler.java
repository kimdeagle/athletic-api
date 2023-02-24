package com.athletic.api.auth.handler;

import com.athletic.api.common.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(ResponseDto.builder().code(ResponseDto.SUCCESS).message("로그아웃 되었습니다.").build());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(result);
    }
}
