package com.gamemini.api.controllers.clientControllers;

import com.gamemini.api.dtos.responses.ApiResponse;
import com.gamemini.api.dtos.responses.review.ReviewData;
import com.gamemini.api.services.pokemon.PokemonService;
import com.gamemini.api.services.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/review")
public class ClientReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private PokemonService pokemonService;

    @GetMapping(value = {"/", ""}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<ReviewData>>> getAll(@RequestParam Map<String, String> params) {
        return reviewService.findAll(params);
    }

    @GetMapping(value = {"/pokemon/{id}/", "/pokemon/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<ReviewData>>> getAllByPokemonId(@PathVariable int id, @RequestParam Map<String, String> params) {
        return reviewService.findAllByPokemonId(id, params);
    }

    @GetMapping(value = {"/{username}/", "/{username}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<ReviewData>>> getAllByUsername(@PathVariable("username") String username, @RequestParam Map<String, String> params) {
        return reviewService.findAllByUsername(username, params);
    }
}
