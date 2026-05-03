package com.gamemini.api.repositories;

import com.gamemini.api.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    /// find whole reviews about pokemon id
    Page<Review> findByPokemonId(int pokemonId, Pageable pageable);
    Page<Review> findByUser(String username, Pageable pageable);
}
