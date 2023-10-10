package com.poke.pokemonsecurity.controller;


import com.poke.pokemonsecurity.dto.ReviewDto;
import com.poke.pokemonsecurity.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping(path = "/pokemon/{pokemonId}/reviews")
    public List<ReviewDto> getReviewsByPokemonId(@PathVariable(value = "pokemonId") int id) {
        return reviewService.getReviewsByPokemonId(id);
    }

    @GetMapping(path = "/pokemon/{pokemonId}/reviews/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable("pokemonId") int pokemonId,
                                                   @PathVariable("id") int reviewId) {
        ReviewDto reviewDto = reviewService.getReviewById(pokemonId,reviewId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @PostMapping(path = "/pokemon/{pokemonId}/reviews")
    public ResponseEntity<ReviewDto> createReview(@PathVariable(value = "pokemonId") int id,@RequestBody ReviewDto reviewDto) {
        return new ResponseEntity<>(reviewService.createReview(id,reviewDto),HttpStatus.CREATED);
    }

    @PutMapping(path = "/pokemon/{pokemonId}/reviews/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable(value = "pokemonId") int pokemonId,
                                                  @PathVariable(value = "id") int reviewId,
                                                  @RequestBody ReviewDto reviewDto) {
        ReviewDto reviewResponse = reviewService.updateReview(pokemonId,reviewId,reviewDto);
        return new ResponseEntity<>(reviewResponse,HttpStatus.OK);
    }

    @DeleteMapping(path = "/pokemon/{pokemonId}/reviews/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable(value = "pokemonId") int pokemonId,
                                               @PathVariable(value = "id") int reviewId) {
        reviewService.deleteReview(pokemonId,reviewId);
        return new ResponseEntity<>("Pokemon has deleted",HttpStatus.OK);
    }

}
