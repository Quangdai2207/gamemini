package com.gamemini.api.controllers;

import com.gamemini.api.dtos.requestes.auth.RequestLogin;
import com.gamemini.api.dtos.requestes.auth.RequestRegister;
import com.gamemini.api.dtos.responses.ApiResponse;
import com.gamemini.api.dtos.responses.auth.AuthData;
import com.gamemini.api.services.auth.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AuthData>> me(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ApiResponse.unauthorize(AuthData
                    .builder()
                    .token("")
                    .username("")
                    .build());
        }
        System.out.println(authentication.toString());
        return ApiResponse.ok(AuthData
                .builder()
                .token("")
                .username(authentication.getName())
                .build(), "Login Successful");
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthData>> login(
            @Valid @RequestBody RequestLogin body,
            HttpServletResponse response
    ) {
        return authService.login(body, response);
    }

    @PostMapping(
            value = {"/register"},
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse<AuthData>> register(@Valid @RequestBody RequestRegister body) {
        return authService.register(body);
    }
}
