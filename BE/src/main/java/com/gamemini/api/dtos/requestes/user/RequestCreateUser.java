package com.gamemini.api.dtos.requestes.user;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RequestCreateUser {
    private String name;
    private String type;
}
