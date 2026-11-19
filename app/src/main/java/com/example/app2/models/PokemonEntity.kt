package com.example.app2.models

data class PokemonEntity(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val image: String?,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val speed: Int
)
