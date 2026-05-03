package com.gamemini.api.configs.springWeb.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamemini.api.dtos.responses.AuthStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/// Xu ly ngoai le chua dang nhap -> Authentication
@Component
@RequiredArgsConstructor
@Slf4j
public class HandlerEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {
        /// Log loi va kiem tra debug ngoai le
//        log.error("Unauthorized: {}", exception.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        /// Tuy chinh Response entry-point thay cho response mac dinh cua spring Security
        AuthStatus<String> authStatus = AuthStatus.<String>builder()
                .success(false)
                .status(401)
                .message("Authentication required")
                .build();

        response.getWriter().write(objectMapper.writeValueAsString(authStatus));
    }
}
