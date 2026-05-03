package com.gamemini.api.services.auth;

import com.gamemini.api.dtos.requestes.auth.RequestLogin;
import com.gamemini.api.dtos.requestes.auth.RequestRegister;
import com.gamemini.api.dtos.responses.ApiResponse;
import com.gamemini.api.dtos.responses.auth.AuthData;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    ResponseEntity<ApiResponse<AuthData>> register(RequestRegister register);
    ResponseEntity<ApiResponse<AuthData>> login(RequestLogin body, HttpServletResponse response);
}
