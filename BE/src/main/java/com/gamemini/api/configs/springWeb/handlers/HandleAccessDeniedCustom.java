package com.gamemini.api.configs.springWeb.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamemini.api.dtos.responses.AuthStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class HandleAccessDeniedCustom implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        /// Log loi kiem tra va debug
//        log.error("Unauthorized: {}", accessDeniedException.getMessage());

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        AuthStatus authStatus = AuthStatus.<String>builder()
                .status(403)
                .success(false)
                .message("Access denied: Insufficient permissions")
                .build();

        response.getWriter().write(objectMapper.writeValueAsString(authStatus));
    }
}
