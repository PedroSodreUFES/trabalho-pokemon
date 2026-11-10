package com.example.app2.models

data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: PokemonSprites,
    val stats: List<PokemonStat>
) {
    val hp: Int
        get() = stats.firstOrNull { it.stat.name == "hp" }?.base_stat ?: 0

    val attack: Int
        get() = stats.firstOrNull { it.stat.name == "attack" }?.base_stat ?: 0

    val defense: Int
        get() = stats.firstOrNull { it.stat.name == "defense" }?.base_stat ?: 0

    val speed: Int
        get() = stats.firstOrNull { it.stat.name == "speed" }?.base_stat ?: 0
}

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