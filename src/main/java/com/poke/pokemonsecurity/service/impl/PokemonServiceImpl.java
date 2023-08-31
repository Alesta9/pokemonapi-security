package com.poke.pokemonsecurity.service.impl;

import com.poke.pokemonsecurity.dto.PokemonDto;
import com.poke.pokemonsecurity.dto.PokemonResponse;
import com.poke.pokemonsecurity.models.Pokemon;
import com.poke.pokemonsecurity.repository.PokemonRepository;
import com.poke.pokemonsecurity.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {

    private final PokemonRepository pokemonRepository;

    @Autowired
    public PokemonServiceImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public PokemonResponse getAllPokemon(int pageNo, int pageSize) {
        //Pageable pageable = (Pageable) PageRequest.of(pageNo,pageSize); deprecated
        Page<Pokemon> pokemons = pokemonRepository.findAll(PageRequest.of(pageNo,pageSize));
        List<Pokemon> listOfPokemon = pokemons.getContent();
        List<PokemonDto> content = listOfPokemon.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

        PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setContent(content); // Returns the page content as List.
        pokemonResponse.setLast(pokemons.isLast()); // Returns whether the current Slice is the last one.
        pokemonResponse.setPageNo(pokemons.getNumber()); // Returns the number of the current Slice
        pokemonResponse.setPageSize(pokemons.getSize()); // Returns the size of the Slice.
        pokemonResponse.setTotalPages(pokemons.getTotalPages());
        pokemonResponse.setTotalElements(pokemons.getTotalElements());

        return pokemonResponse;
    }

    @Override
    public PokemonDto getPokemonById(int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new IllegalStateException("Pokemon could not be found"));
        return mapToDto(pokemon);
    }

    @Override
    public PokemonDto createPokemon(PokemonDto pokemonDto) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        Pokemon newPokemon = pokemonRepository.save(pokemon);
        // Saved the pokemon and now we have a Pokemon with id. DB setting the id.
        PokemonDto pokemonResponse = new PokemonDto();
        pokemonResponse.setName(newPokemon.getName());
        pokemonResponse.setType(newPokemon.getType());
        pokemonResponse.setId(newPokemon.getId());
        return pokemonResponse;
    }

    @Override
    public PokemonDto updatePokemon(PokemonDto pokemonDto, int id) {
        Pokemon pokemon = pokemonRepository.findById(id)
                                           .orElseThrow(() -> new IllegalArgumentException("pokemon could not be updated"));
        pokemon.setType(pokemonDto.getType());
        pokemon.setName(pokemonDto.getName());

        Pokemon updatedPokemon = pokemonRepository.save(pokemon);

        return mapToDto(updatedPokemon);

    }

    @Override
    public void deletePokemonById(int id) {
        Pokemon pokemon = pokemonRepository.findById(id)
                                           .orElseThrow(() -> new IllegalArgumentException("pokemon could not be deleted"));
        pokemonRepository.delete(pokemon);
    }

    private PokemonDto mapToDto(Pokemon p) {
        PokemonDto pokemonDto = new PokemonDto();
        pokemonDto.setType(p.getType());
        pokemonDto.setId(p.getId());
        pokemonDto.setName(p.getName());
        return pokemonDto;
    }


}
