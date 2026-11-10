package com.example.app2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.app2.models.PokemonEntity

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun inserir(pokemon: PokemonEntity)

    @Query("DELETE FROM pokemons_favoritos WHERE id = :pokemonId")
    fun remover(pokemonId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM pokemons_favoritos WHERE id = :pokemonId)")
    fun estaFavoritado(pokemonId: Int): LiveData<Boolean>

    @Query("SELECT * FROM pokemons_favoritos ORDER BY nome ASC")
    fun listarFavoritos(): LiveData<List<PokemonEntity>>
}
