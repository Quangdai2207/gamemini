package com.gamemini.api.dtos.responses.review;

import com.gamemini.api.entities.Pokemon;
import com.gamemini.api.entities.UserEntity;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ReviewData {
    private String title;
    private String content;
    private Pokemon pokemon;
    private UserEntity user;
}
