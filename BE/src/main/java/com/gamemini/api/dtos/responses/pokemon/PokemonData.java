package com.gamemini.api.dtos.responses.pokemon;

import com.gamemini.api.entities.Review;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PokemonData<T> {
    private String name;
    private String type;
    List<Review> reviews;
}
