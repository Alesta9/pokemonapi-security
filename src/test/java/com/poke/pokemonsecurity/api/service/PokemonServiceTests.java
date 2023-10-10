package com.poke.pokemonsecurity.api.service;

import com.poke.pokemonsecurity.dto.PokemonDto;
import com.poke.pokemonsecurity.dto.PokemonResponse;
import com.poke.pokemonsecurity.models.Pokemon;
import com.poke.pokemonsecurity.repository.PokemonRepository;
import com.poke.pokemonsecurity.service.PokemonService;
import com.poke.pokemonsecurity.service.impl.PokemonServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PokemonServiceTests {

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private PokemonServiceImpl pokemonService;

    @Test
    void PokemonService_CreatePokemon_ReturnsPokemonDto() {

        // Arrange
        Pokemon pokemon = Pokemon.builder()
                                 .name("pika")
                                 .type("elec").build();

        PokemonDto pokemonDto = PokemonDto.builder()
                                .name(pokemon.getName())
                                .type(pokemon.getType()).build();
        // Act
        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        PokemonDto savedPokemon = pokemonService.createPokemon(pokemonDto);
        // Assert
        Assertions.assertThat(savedPokemon).isNotNull();
    }

    @Test
    void PokemonService_GetAllPokemon_ReturnsResponseDto() {
        // ARRANGE
        Page<Pokemon> pokemons = Mockito.mock(Page.class);
        // This will generate a fake Page Class
        // ACT
        when(pokemonRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pokemons);

        PokemonResponse savedPokemon = pokemonService.getAllPokemon(1,10);
        // ASSERT
        Assertions.assertThat(savedPokemon).isNotNull();
    }

    @Test
    void PokemonService_GetPokemonById_ReturnsPokemonDto() {
        // Arrange
        Pokemon pokemon = Pokemon.builder().name("pika").type("elec").build();

        // Act
        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));
        PokemonDto savedPokemon = pokemonService.getPokemonById(1);
        // Assert
        Assertions.assertThat(savedPokemon).isNotNull();
    }

    @Test
    void PokemonService_updatePokemon_ReturnsPokemonDto() {
        // Arrange
        Pokemon pokemon = Pokemon.builder().name("pika").type("elec").build();
        PokemonDto pokemonDto = PokemonDto.builder().name("pika").type("elec").build();
        // Act
        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));
        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);
        // EXECUTE THE METHOD
        PokemonDto savedPokemon = pokemonService.updatePokemon(pokemonDto,1);
        // Assert
        Assertions.assertThat(savedPokemon).isNotNull();
    }

    @Test
    void PokemonService_deletePokemonById_ReturnsPokemonDto() {
        // Arrange
        Pokemon pokemon = Pokemon.builder().name("pika").type("elec").build();

        // Act
        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));

        // Assert // WE USING assertAll BECAUSE IT RETURNS VOID VALUE
        assertAll(() -> pokemonService.deletePokemonById(1));
    }

}
