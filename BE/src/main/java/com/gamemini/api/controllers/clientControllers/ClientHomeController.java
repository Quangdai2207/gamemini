package com.gamemini.api.controllers.clientControllers;

import com.gamemini.api.dtos.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({ "/", ""})
public class ClientHomeController {

    @GetMapping({"/index", "/", ""})
    public ResponseEntity<ApiResponse<String>> index() {
        return ApiResponse.ok("Welcome to game mini");
    }
}
