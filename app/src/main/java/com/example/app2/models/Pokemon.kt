package com.example.app2.models

data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: PokemonSprites,
    val stats: List<PokemonStat>
)

data class PokemonSprites(
    val front_default: String?
)

data class PokemonStat(
    val base_stat: Int,
    val stat: StatInfo
)

data class StatInfo(
    val name: String
)