package com.example.app2.api

import com.example.app2.models.Pokemon
import com.example.app2.models.PokemonListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET("pokemon/{name}")
    fun buscarPokemon(
        @Path("name") nome: String
    ): Call<Pokemon>

    @GET("pokemon")
    fun listarPokemons(
        @Query("limit") limite: Int,
        @Query("offset") deslocamento: Int
    ): Call<PokemonListResponse>
}