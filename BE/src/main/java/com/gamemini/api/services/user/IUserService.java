package com.gamemini.api.services.user;

import com.gamemini.api.dtos.requestes.auth.RequestRegister;
import com.gamemini.api.entities.UserEntity;

public interface IUserService {
    UserEntity findByUsername(String username);
}
