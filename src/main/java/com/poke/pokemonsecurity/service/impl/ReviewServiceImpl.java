package com.poke.pokemonsecurity.service.impl;

import com.poke.pokemonsecurity.dto.ReviewDto;
import com.poke.pokemonsecurity.models.Pokemon;
import com.poke.pokemonsecurity.models.Review;
import com.poke.pokemonsecurity.repository.PokemonRepository;
import com.poke.pokemonsecurity.repository.ReviewRepository;
import com.poke.pokemonsecurity.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final PokemonRepository pokemonRepository;
    private static final String EXCEPTION="review does not belong to this poki";
    private static final String REVIEW_EXCEPTION = "pokemon with that review not found";
    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, PokemonRepository pokemonRepository) {
        this.reviewRepository = reviewRepository;
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public List<ReviewDto> getReviewsByPokemonId(int id) {

        List<Review> reviews = reviewRepository.findByPokemonId(id);

        return reviews.stream().map(this::mapToDto).toList();
    }

    @Override
    public ReviewDto getReviewById(int pokemonId, int reviewId) {

        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                                           .orElseThrow(() -> new IllegalArgumentException(REVIEW_EXCEPTION));
        Review review = reviewRepository.findById(reviewId)
                                        .orElseThrow(() -> new IllegalStateException(REVIEW_EXCEPTION));

        if(review.getPokemon().getId() != pokemon.getId()) {
            throw new IllegalStateException(EXCEPTION);
        }
        return mapToDto(review);

    }

    @Override
    public ReviewDto createReview(int pokemonId, ReviewDto reviewDto) {

        Review review = mapToEntity(reviewDto);

        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                                           .orElseThrow(() -> new IllegalArgumentException(REVIEW_EXCEPTION));

        review.setPokemon(pokemon);

        Review newReview = reviewRepository.save(review);

        return mapToDto(newReview);

    }

    @Override
    public ReviewDto updateReview(int pokemonId, int reviewId, ReviewDto reviewDto) {

        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                                           .orElseThrow(() -> new IllegalArgumentException(REVIEW_EXCEPTION));
        Review review = reviewRepository.findById(reviewId)
                                        .orElseThrow(() -> new IllegalStateException(REVIEW_EXCEPTION));

        if(review.getPokemon().getId() != pokemon.getId()) {
            throw new IllegalStateException(EXCEPTION);
        }

        review.setTitle(reviewDto.getTitle());
        review.setStars(reviewDto.getStars());
        review.setContent(reviewDto.getContent());

        Review updatedReview = reviewRepository.save(review);

        return mapToDto(updatedReview);


    }

    @Override
    public void deleteReview(int pokemonId, int reviewId) {

        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                                           .orElseThrow(() -> new IllegalArgumentException(REVIEW_EXCEPTION));
        Review review = reviewRepository.findById(reviewId)
                                        .orElseThrow(() -> new IllegalStateException(REVIEW_EXCEPTION));

        if(review.getPokemon().getId() != pokemon.getId()) {
            throw new IllegalStateException(EXCEPTION);
        }

        reviewRepository.delete(review);

    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setTitle(reviewDto.getTitle());
        review.setStars(reviewDto.getStars());
        review.setId(reviewDto.getId());
        review.setContent(reviewDto.getContent());
        return review;
    }

    private ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setId(review.getId());
        return reviewDto;
    }
}
