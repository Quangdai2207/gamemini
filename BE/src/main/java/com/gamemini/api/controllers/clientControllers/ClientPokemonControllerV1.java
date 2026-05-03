package com.gamemini.api.controllers.clientControllers;

import com.gamemini.api.dtos.requestes.Pagination;
import com.gamemini.api.dtos.requestes.pokemon.RequestCreatePokemon;
import com.gamemini.api.dtos.requestes.pokemon.RequestUpdatePokemon;
import com.gamemini.api.dtos.responses.ApiResponse;
import com.gamemini.api.dtos.responses.pokemon.PokemonData;
import com.gamemini.api.entities.Pokemon;
import com.gamemini.api.mappers.PokemonMapper;
import com.gamemini.api.services.pokemon.PokemonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/api/v1/pokemon")
public class ClientPokemonControllerV1 {
    @Autowired
    private PokemonService pokemonService;

    @GetMapping({"/details/{id}"})
    public ResponseEntity<ApiResponse<PokemonData>> getById(@PathVariable int id) {
        return ApiResponse.ok(PokemonMapper.toPokemonData(pokemonService.findById(id)));
    }

    /// Use @RequestParam with argument required = false if user not pass value for page or size.
    /// Use default values with page is "0" and size is "10" for paginate.
    @GetMapping({"/get-all"})
    public ResponseEntity<ApiResponse<List<PokemonData>>> getAll(@ModelAttribute Pagination pagination) {
        return pokemonService.getAll(pagination);
    }


    @PostMapping(
            value = {"/add-new"},
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse<Pokemon>> create(@RequestBody @Valid RequestCreatePokemon body) {
        return ApiResponse.created(pokemonService.add(body), "Pokemon created");
    }


    @PutMapping(value = {"/edit/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Pokemon>> edit(@RequestBody @Valid RequestUpdatePokemon body, @PathVariable int id) {
        Pokemon updated = pokemonService.update(body, id);
        return ApiResponse.ok(PokemonMapper.toPokemon(body, updated), "Updated Successfully");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Pokemon>> remove(@PathVariable int id) {
        pokemonService.remove(id);
        return ApiResponse.ok("Deleted successfully.");
    }
}
