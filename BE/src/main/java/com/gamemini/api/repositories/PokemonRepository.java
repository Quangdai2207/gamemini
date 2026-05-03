package com.gamemini.api.repositories;

import com.gamemini.api.entities.Pokemon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
    Page<Pokemon> findAllByOwner(String email, Pageable pageable);
}
