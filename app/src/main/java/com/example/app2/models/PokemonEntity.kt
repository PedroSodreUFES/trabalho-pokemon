package com.example.app2.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons_favoritos")
data class PokemonEntity(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "nome")
    val name: String,

    @ColumnInfo(name = "altura")
    val height: Int,

    @ColumnInfo(name = "peso")
    val weight: Int,

    @ColumnInfo(name = "imagem")
    val image: String?,

    @ColumnInfo(name = "hp")
    val hp: Int,

    @ColumnInfo(name = "ataque")
    val attack: Int,

    @ColumnInfo(name = "defesa")
    val defense: Int,

    @ColumnInfo(name = "velocidade")
    val speed: Int
)
