package com.gamemini.api.mappers;

import com.gamemini.api.dtos.requestes.auth.RequestLogin;
import com.gamemini.api.dtos.requestes.auth.RequestRegister;
import com.gamemini.api.entities.UserEntity;

public class UserMapper {

    public static UserEntity requestLoginToUser(RequestLogin login) {
        return UserEntity.builder()
                .username(login.getUsername())
                .password(login.getPassword())
                .build();
    }

    public static UserEntity requestRegisterToUser(RequestRegister body) {
        return UserEntity.builder()
                .username(body.getUsername())
                .password(body.getPassword())
                .build();
    }
}
