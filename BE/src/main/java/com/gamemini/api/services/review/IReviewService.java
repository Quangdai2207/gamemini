package com.gamemini.api.services.review;

import com.gamemini.api.dtos.responses.ApiResponse;
import com.gamemini.api.dtos.responses.review.ReviewData;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IReviewService {
    ResponseEntity<ApiResponse<List<ReviewData>>> findAll(Map<String, String> params);
    ResponseEntity<ApiResponse<List<ReviewData>>> findAllByPokemonId(int pokemonId, Map<String, String> params);
    ResponseEntity<ApiResponse<List<ReviewData>>> findAllByUsername(String username, Map<String, String> params);
}
