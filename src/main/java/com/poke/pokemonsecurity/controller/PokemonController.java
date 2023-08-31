package com.poke.pokemonsecurity.controller;

import com.poke.pokemonsecurity.dto.PokemonDto;
import com.poke.pokemonsecurity.dto.PokemonResponse;
import com.poke.pokemonsecurity.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PokemonController {

    private final PokemonService pokemonService;

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/pokemon")
    public ResponseEntity<PokemonResponse> getPokemons(
        @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
        @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize
        // pageSize --> size of Every Page , pageNo --> number of Current Page
    ) {
        return new ResponseEntity<>(pokemonService.getAllPokemon(pageNo,pageSize), HttpStatus.OK);
    }

    @GetMapping(path = "/pokemon/{id}")
    public ResponseEntity<PokemonDto> getPokemon(@PathVariable("id") int id) {

        return ResponseEntity.ok(pokemonService.getPokemonById(id));

    }

    @PostMapping(path = "/pokemon/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PokemonDto> createPokemon(@RequestBody PokemonDto pokemonDto) {

        return new ResponseEntity<>(pokemonService.createPokemon(pokemonDto),HttpStatus.CREATED);

    }

    @PutMapping(path = "/pokemon/{id}/update")
    public ResponseEntity<PokemonDto> updatePokemon(@RequestBody PokemonDto pokemonDto,@PathVariable("id") int id) {
        PokemonDto response = pokemonService.updatePokemon(pokemonDto,id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping(path = "/pokemon/{id}/delete")
    public ResponseEntity<String> deletePokemon(@PathVariable("id") int id) {
        pokemonService.deletePokemonById(id);
        return new ResponseEntity<>("Pokemon with id: " + id + " is Deleted",HttpStatus.OK);
    }



}
