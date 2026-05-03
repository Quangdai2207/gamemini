package com.gamemini.api.services.user;

import com.gamemini.api.entities.UserEntity;
import com.gamemini.api.exceptions.NotfoundException;
import com.gamemini.api.repositories.RoleRepository;
import com.gamemini.api.repositories.UserRepository;
import com.gamemini.api.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotfoundException("User not found"));
    }
}
