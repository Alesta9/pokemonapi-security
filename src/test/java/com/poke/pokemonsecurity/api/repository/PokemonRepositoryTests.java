package com.poke.pokemonsecurity.api.repository;

import com.poke.pokemonsecurity.models.Pokemon;
import com.poke.pokemonsecurity.repository.PokemonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) // Embedded DB
class PokemonRepositoryTests {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    void PokemonRepository_SaveAll_ReturnSavedPokemon(){
        // Tests doesn't return anything so it has to be void
        // Arrange
        Pokemon pokemon = Pokemon.builder()
                                 .name("Pikapika")
                                 .type("electric").build();

        // Act
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        // Assert
        Assertions.assertThat(savedPokemon).isNotNull();
        Assertions.assertThat(savedPokemon.getId()).isPositive();
    }

    @Test
    void PokemonRepository_FindAll_ReturnMoreThanOnePokemon() {
        // Arrange
        Pokemon pokemon = Pokemon.builder().name("pika").type("elec").build();
        Pokemon pokemon2 = Pokemon.builder().name("pika").type("elec").build();
        // Act
        pokemonRepository.save(pokemon);
        pokemonRepository.save(pokemon2);
        List<Pokemon> pokemonList = pokemonRepository.findAll();
        // Assert
        Assertions.assertThat(pokemonList).hasSize(2).isNotNull();
    }
    @Test
    void PokemonRepository_FindId_ReturnTheExistenceById() {
        // Arrange
        Pokemon pokemon = Pokemon.builder().name("pikachu").type("electric").build();
        // Act
        pokemonRepository.save(pokemon);
        Optional<Pokemon> pokemonReturn = pokemonRepository.findById(pokemon.getId());
        // Assert
        Assertions.assertThat(pokemonReturn).isNotNull();

    }

    @Test
    void PokemonRepository_FindByType_ReturnPokemonNotNull() {
        // Arrange
        Pokemon pokemon = Pokemon.builder().name("pikachu").type("electric").build();
        // Act
        pokemonRepository.save(pokemon);
        Optional<Pokemon> pokemonReturn = pokemonRepository.findByType(pokemon.getType());
        // Assert
        Assertions.assertThat(pokemonReturn).isNotNull();
    }
}
