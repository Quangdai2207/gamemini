package com.gamemini.api.repositories;

import com.gamemini.api.entities.Role;
import com.gamemini.api.entities.UserEntity;
import com.gamemini.api.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    List<UserRole> findByRole(Role role);

    List<UserRole> findByUser(UserEntity user);

    UserRole findByRoleAndUser(Role role, UserEntity user);

    UserRole findById(int id);
}

