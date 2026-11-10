
package com.example.app2.database

import androidx.lifecycle.LiveData
import com.example.app2.models.PokemonEntity
import java.util.concurrent.Executors

class PokemonRepository(
    private val pokemonDao: PokemonDao
) {
    private val executor = Executors.newSingleThreadExecutor()

    fun inserir(pokemon: PokemonEntity) {
        executor.execute {
            pokemonDao.inserir(pokemon)
        }
    }

    fun remover(pokemonId: Int) {
        executor.execute {
            pokemonDao.remover(pokemonId)
        }
    }

    fun estaFavoritado(pokemonId: Int): LiveData<Boolean> {
        return pokemonDao.estaFavoritado(pokemonId)
    }

    fun listarFavoritos(): LiveData<List<PokemonEntity>> {
        return pokemonDao.listarFavoritos()
    }
}
