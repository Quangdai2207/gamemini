package com.gamemini.api.dtos.responses;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthStatus<T> {
    private boolean success;
    private int status;
    private T message;
}
