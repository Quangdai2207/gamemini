package com.gamemini.api.services.review;

import com.gamemini.api.dtos.responses.ApiResponse;
import com.gamemini.api.dtos.responses.PaginationMeta;
import com.gamemini.api.dtos.responses.review.ReviewData;
import com.gamemini.api.entities.Pokemon;
import com.gamemini.api.entities.Review;
import com.gamemini.api.entities.UserEntity;
import com.gamemini.api.exceptions.NotfoundException;
import com.gamemini.api.mappers.ReviewMapper;
import com.gamemini.api.repositories.PokemonRepository;
import com.gamemini.api.repositories.ReviewRepository;
import com.gamemini.api.repositories.UserRepository;
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
public class ReviewService implements IReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<ApiResponse<List<ReviewData>>> findAll(Map<String, String> params) {
        if (!checkKey(params)) throw new NotfoundException("Invalid key");
        int page = Integer.parseInt(params.getOrDefault("page", "0"));
        int size = Integer.parseInt(params.getOrDefault("size", "10"));

        Pageable pageable = PageRequest.of(page, size);
        Page<Review> pages = reviewRepository.findAll(pageable);
        PaginationMeta meta = PaginationMeta.builder()
                .page(pages.getNumber())
                .size(pages.getSize())
                .totalElements(pages.getTotalElements())
                .totalPages(pages.getTotalPages())
                .hasNext(pages.hasNext())
                .hasPrevious(pages.hasPrevious())
                .build();

        return ApiResponse.ok(pages.getContent().stream().map(ReviewMapper::toReviewData).collect(Collectors.toList()), meta);
    }

    @Override
    public ResponseEntity<ApiResponse<List<ReviewData>>> findAllByPokemonId(int pokemonId, Map<String, String> params) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new NotfoundException("Pokemon not found"));
        if (!checkKey(params)) throw new NotfoundException("Invalid key");

        int page = Integer.parseInt(params.getOrDefault("page", "0"));
        int size = Integer.parseInt(params.getOrDefault("size", "10"));

        Pageable pageable = PageRequest.of(page, size);
        Page<Review> pages = reviewRepository.findByPokemonId(pokemon.getId(), pageable);
        PaginationMeta meta = PaginationMeta.builder()
                .page(pages.getNumber())
                .size(pages.getSize())
                .totalElements(pages.getTotalElements())
                .totalPages(pages.getTotalPages())
                .hasNext(pages.hasNext())
                .hasPrevious(pages.hasPrevious())
                .build();

        return ApiResponse.ok(
                pages.getContent().stream().map(ReviewMapper::toReviewData).collect(Collectors.toList()),
                meta
        );
    }

    @Override
    public ResponseEntity<ApiResponse<List<ReviewData>>> findAllByUsername(String username, Map<String, String> params) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new NotfoundException("User not found"));

        if (!checkKey(params)) throw new NotfoundException("Invalid key");

        int page = Integer.parseInt(params.getOrDefault("page", "0"));
        int size = Integer.parseInt(params.getOrDefault("size", "10"));

        Pageable pageable = PageRequest.of(page, size);
        Page<Review> pages = reviewRepository.findByUser(user.getUsername(), pageable);

        PaginationMeta meta = PaginationMeta.builder()
                .page(pages.getNumber())
                .size(pages.getSize())
                .totalElements(pages.getTotalElements())
                .totalPages(pages.getTotalPages())
                .hasNext(pages.hasNext())
                .hasPrevious(pages.hasPrevious())
                .build();

        return ApiResponse.ok(
                pages.getContent().stream().map(ReviewMapper::toReviewData).collect(Collectors.toList()),
                meta
        );
    }


    public boolean checkKey(Map<String, String> params) {
        boolean legalKey = true;

        Set<String> applyKey = Set.of("page", "size");

        for (String key : params.keySet()) {
            if (!applyKey.contains(key)) legalKey = false;
        }

        return legalKey;
    }
}
