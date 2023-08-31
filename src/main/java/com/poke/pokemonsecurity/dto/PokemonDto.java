package com.poke.pokemonsecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PokemonDto {

    private int id;
    private String name;
    private String type;

}
