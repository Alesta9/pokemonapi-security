package com.poke.pokemonsecurity.api.repository;

import com.poke.pokemonsecurity.models.Pokemon;
import com.poke.pokemonsecurity.repository.PokemonRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) // Embedded DB
public class PokemonRepositoryTests {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test // Tests doesnt return anything so it has to be void
    public void PokemonRepository_SaveAll_ReturnSavedPokemon(){

        // Arrange
        Pokemon pokemon = Pokemon.builder()
                                 .name("Pikapika")
                                 .type("electric").build();

        // Act
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        // Assert
        Assertions.assertThat(savedPokemon).isNotNull();
        Assertions.assertThat(savedPokemon.getId()).isGreaterThan(0);
    }

    @Test
    public void PokemonRepository_FindAll_ReturnMoreThanOnePokemon() {
        // Arrange
        Pokemon pokemon = Pokemon.builder().name("pika").type("elec").build();
        Pokemon pokemon2 = Pokemon.builder().name("pika").type("elec").build();
        // Act
        pokemonRepository.save(pokemon);
        pokemonRepository.save(pokemon2);
        List<Pokemon> pokemonList = pokemonRepository.findAll();
        // Assert
        Assertions.assertThat(pokemonList).isNotNull();
        Assertions.assertThat(pokemonList.size()).isEqualTo(2);
    }
    @Test
    public void PokemonRepository_FindId_ReturnTheExistenceById() {
        // Arrange
        Pokemon pokemon = Pokemon.builder().name("pikachu").type("electric").build();
        // Act
        pokemonRepository.save(pokemon);
        Optional<Pokemon> pokemonReturn = pokemonRepository.findById(pokemon.getId());
        // Assert
        Assertions.assertThat(pokemonReturn).isNotNull();

    }

    @Test
    public void PokemonRepository_FindByType_ReturnPokemonNotNull() {
        // Arrange
        Pokemon pokemon = Pokemon.builder().name("pikachu").type("electric").build();
        // Act
        pokemonRepository.save(pokemon);
        Optional<Pokemon> pokemonReturn = pokemonRepository.findByType(pokemon.getType());
        // Assert
        Assertions.assertThat(pokemonReturn).isNotNull();
    }
}
