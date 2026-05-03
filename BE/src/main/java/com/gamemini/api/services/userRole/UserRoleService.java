package com.gamemini.api.services.userRole;

import com.gamemini.api.entities.Role;
import com.gamemini.api.entities.UserEntity;
import com.gamemini.api.entities.UserRole;
import com.gamemini.api.exceptions.BadRequestException;
import com.gamemini.api.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService implements  IUserRole {
    @Autowired
    private UserRoleRepository userRoleRepository;


    @Override
    public UserRole add(Role role, UserEntity user) {
        if (user == null ||  role == null) throw new BadRequestException("user or role is missing");
        UserRole userRole = UserRole.builder().role(role).user(user).build();
        return userRoleRepository.save(userRole);
    }
}
