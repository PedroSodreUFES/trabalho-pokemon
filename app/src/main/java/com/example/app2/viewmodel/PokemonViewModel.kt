package com.example.app2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.app2.database.PokemonRepository
import com.example.app2.models.PokemonEntity

class PokemonViewModel : ViewModel() {
    private val repository = PokemonRepository()

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
