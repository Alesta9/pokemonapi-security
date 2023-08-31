package com.poke.pokemonsecurity.repository;

import com.poke.pokemonsecurity.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Integer> {

    List<Review> findByPokemonId(int pokemonId);

}
