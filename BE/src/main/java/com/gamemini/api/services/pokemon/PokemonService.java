package com.gamemini.api.services.pokemon;

import com.gamemini.api.dtos.requestes.Pagination;
import com.gamemini.api.dtos.requestes.pokemon.RequestCreatePokemon;
import com.gamemini.api.dtos.requestes.pokemon.RequestUpdatePokemon;
import com.gamemini.api.dtos.responses.ApiResponse;
import com.gamemini.api.dtos.responses.PaginationMeta;
import com.gamemini.api.dtos.responses.pokemon.PokemonData;
import com.gamemini.api.entities.Pokemon;
import com.gamemini.api.entities.UserEntity;
import com.gamemini.api.exceptions.IllegalParamException;
import com.gamemini.api.exceptions.NotfoundException;
import com.gamemini.api.mappers.PokemonMapper;
import com.gamemini.api.repositories.PokemonRepository;
import com.gamemini.api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PokemonService implements IPokemonService {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Pokemon findById(int id) {
        return pokemonRepository.findById(id).orElseThrow(() -> new NotfoundException("Pokemon with id " + id + " not found"));
    }

    /// Get list without pagination
    @Override
    public List<PokemonData> findAll() {
        return pokemonRepository.findAll().stream().map(PokemonMapper::toPokemonData).collect(Collectors.toList());
    }

    /// Get list with Pagination
    @Override
    public List<PokemonData> findAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Pokemon> data = pokemonRepository.findAll(pageable);

        return data.getContent().stream().map(PokemonMapper::toPokemonData).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<ApiResponse<List<PokemonData>>> getAll(int page, int size) {
        /// Paginate Object:
        Pageable pageable = PageRequest.of(page, size);
        /// init Page Object with type Pokemon and pass pageable
        Page<Pokemon> pokemonList = pokemonRepository.findAll(pageable);
        /// mapper dto pokemon -> pokemonData
        List<PokemonData> data = pokemonList.getContent().stream().map(PokemonMapper::toPokemonData).collect(Collectors.toUnmodifiableList());

        // Create meta pagination
        PaginationMeta meta = PaginationMeta.builder().page(pokemonList.getNumber()).size(pokemonList.getSize()).totalElements(pokemonList.getTotalElements()).totalPages(pokemonList.getTotalPages()).hasNext(pokemonList.hasNext()).hasPrevious(pokemonList.hasPrevious()).build();

        return ApiResponse.ok(data, meta);
    }

    @Override
    public ResponseEntity<ApiResponse<List<PokemonData>>> getAll(Pagination pagination) {
        int page = pagination.getPage();
        int size = Math.min(pagination.getSize(), 100);
        /// Set gia tri mac dinh cho page  = 0 va size = 10 moi page tinh tu page 0 neu nhu nguoi dung khong thuc hien truyen
        /// gia tri query string URL.
        return getAll(page, size);
    }

    @Transactional
    @Override
    public Pokemon add(RequestCreatePokemon body) {
        Pokemon pokemon = Pokemon.builder()
                .name(body.getName()).type(body.getType()).build();

        return pokemonRepository.save(pokemon);
    }

    @Transactional
    @Override
    public void remove(int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new NotfoundException("Pokemon with id " + id + " not found"));

        pokemonRepository.delete(pokemon);
    }

    @Transactional
    @Override
    public Pokemon update(RequestUpdatePokemon body, int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new NotfoundException("Pokemon with id " + id + " not found"));

        pokemon.setType(body.getType());
        return pokemonRepository.save(pokemon);
    }

    @Override
    public ResponseEntity<ApiResponse<List<PokemonData>>> getAllByUser(String username, Map<String, String> params) {
        Set<String> legalKeys = Set.of("page", "size");

        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new NotfoundException("User with name " + username + " not found"));

        for (String key : params.keySet()) {
            if (!legalKeys.contains(key)) throw new IllegalParamException("key " + key + " not found");
        }

        int page = Integer.parseInt(params.getOrDefault("page", "0"));
        int size = Integer.parseInt(params.getOrDefault("size", "10"));

        Pageable pageable = PageRequest.of(page, size);
        Page<Pokemon> pages = pokemonRepository.findAllByOwner(userEntity.getUsername(), pageable);
        List<PokemonData> pokemon = pages.getContent().stream().map(PokemonMapper::toPokemonData).collect(Collectors.toList());

        PaginationMeta meta = PaginationMeta.builder()
                .page(pages.getNumber())
                .size(pages.getSize())
                .totalElements(pages.getTotalElements())
                .totalPages(pages.getTotalPages())
                .hasNext(pages.hasNext())
                .hasPrevious(pages.hasPrevious())
                .build();

        return ApiResponse.ok(pokemon, meta);
    }

}