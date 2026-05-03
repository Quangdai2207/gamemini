package com.gamemini.api.services.userRole;

import com.gamemini.api.entities.Role;
import com.gamemini.api.entities.UserEntity;
import com.gamemini.api.entities.UserRole;

public interface IUserRole {
    UserRole add(Role role, UserEntity user);
}
