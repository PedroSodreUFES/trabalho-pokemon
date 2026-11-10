package com.example.app2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.app2.database.PokemonDatabase
import com.example.app2.database.PokemonRepository
import com.example.app2.models.PokemonEntity

class PokemonViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PokemonRepository

    init {
        val pokemonDao = PokemonDatabase
            .getDatabase(application)
            .pokemonDao()

        repository = PokemonRepository(pokemonDao)
    }

    fun inserir(pokemon: PokemonEntity) {
        repository.inserir(pokemon)
    }

    fun remover(pokemonId: Int) {
        repository.remover(pokemonId)
    }

    fun estaFavoritado(pokemonId: Int): LiveData<Boolean> {
        return repository.estaFavoritado(pokemonId)
    }

    fun listarFavoritos(): LiveData<List<PokemonEntity>> {
        return repository.listarFavoritos()
    }
}
