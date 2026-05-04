package com.gamemini.api.dtos.responses.auth;

import com.gamemini.api.entities.UserEntity;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthData {
    private String username;
}
