package com.poke.pokemonsecurity.service;

import com.poke.pokemonsecurity.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    List<ReviewDto> getReviewsByPokemonId(int id);

    ReviewDto getReviewById(int pokemonId, int reviewId);

    ReviewDto createReview(int id, ReviewDto reviewDto);

    ReviewDto updateReview(int pokemonId, int reviewId, ReviewDto reviewDto);

    void deleteReview(int pokemonId, int reviewId);
}
