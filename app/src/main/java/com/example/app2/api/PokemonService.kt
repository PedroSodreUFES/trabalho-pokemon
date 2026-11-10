package com.example.app2.api

import com.example.app2.models.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("pokemon/{name}")
    fun buscarPokemon(
        @Path("name") nome: String
    ): Call<Pokemon>

}