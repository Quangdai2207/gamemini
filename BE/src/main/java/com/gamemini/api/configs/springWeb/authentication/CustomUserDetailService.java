package com.gamemini.api.configs.springWeb.authentication;

import com.gamemini.api.entities.Role;
import com.gamemini.api.entities.UserEntity;
import com.gamemini.api.entities.UserRole;
import com.gamemini.api.repositories.UserRepository;
import com.gamemini.api.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        List<UserRole> roles = userRoleRepository.findByUser(user);

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(mapRoleToGrantedAuthority(roles))
                .build();
    }

    private Collection<GrantedAuthority> mapRoleToGrantedAuthority(List<UserRole> roles) {
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().getName().toUpperCase())).collect(Collectors.toList());
    }
}
