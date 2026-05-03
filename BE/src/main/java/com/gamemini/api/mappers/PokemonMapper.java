package com.gamemini.api.mappers;

import com.gamemini.api.dtos.requestes.pokemon.RequestUpdatePokemon;
import com.gamemini.api.dtos.responses.pokemon.PokemonData;
import com.gamemini.api.entities.Pokemon;

public class PokemonMapper<T> {

    public static <T> PokemonData<T> toPokemonData(Pokemon pokemon) {
        return PokemonData.<T>builder()
                .name(pokemon.getName())
                .type(pokemon.getType())
                .reviews(pokemon.getReviews())
                .build();
    }

    public static Pokemon toPokemon(RequestUpdatePokemon body, Pokemon pojo) {
        pojo.setType(body.getType());
        return pojo;
    }
}
