package com.poke.pokemonsecurity.service;


import com.poke.pokemonsecurity.dto.PokemonDto;
import com.poke.pokemonsecurity.dto.PokemonResponse;

public interface PokemonService {


    PokemonResponse getAllPokemon(int pageNo, int pageSize);

    PokemonDto getPokemonById(int id);

    PokemonDto createPokemon(PokemonDto pokemonDto);

    PokemonDto updatePokemon(PokemonDto pokemonDto, int id);

    void deletePokemonById(int id);
}
