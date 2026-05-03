package com.gamemini.api.services.pokemon;


import com.gamemini.api.dtos.requestes.Pagination;
import com.gamemini.api.dtos.requestes.pokemon.RequestCreatePokemon;
import com.gamemini.api.dtos.requestes.pokemon.RequestUpdatePokemon;
import com.gamemini.api.dtos.responses.ApiResponse;
import com.gamemini.api.dtos.responses.pokemon.PokemonData;
import com.gamemini.api.entities.Pokemon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IPokemonService {
    Pokemon findById(int id);

    List<PokemonData> findAll();
    List<PokemonData> findAll(int pageNo, int pageSize);

    ResponseEntity<ApiResponse<List<PokemonData>>> getAll(int page, int size);
    ResponseEntity<ApiResponse<List<PokemonData>>> getAll(Pagination pagintion);

    Pokemon add(RequestCreatePokemon body);

    void remove(int id);

    Pokemon update(RequestUpdatePokemon body, int id);

    ResponseEntity<ApiResponse<List<PokemonData>>> getAllByUser(String email, Map<String, String> params);
}
