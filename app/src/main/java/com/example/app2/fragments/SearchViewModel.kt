package com.example.app2.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app2.api.RetrofitClient
import com.example.app2.models.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon> = _pokemon
    private val _erro = MutableLiveData<String>()
    val erro: LiveData<String> = _erro

    fun buscar(nome: String) {
        val nomeFormatado = nome.trim().lowercase()
        if (nomeFormatado.isBlank()) {
            _erro.value = "Digite um Pokémon!"
            return
        }
        RetrofitClient.pokemonService
            .buscarPokemon(nomeFormatado)
            .enqueue(object : Callback<Pokemon> {
                override fun onResponse(
                    call: Call<Pokemon>,
                    response: Response<Pokemon>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _pokemon.postValue(it)
                        }
                    } else {
                        _erro.postValue("Nenhum Pokémon encontrado!")
                    }
                }
                override fun onFailure(
                    call: Call<Pokemon>,
                    t: Throwable
                ) {
                    _erro.postValue("Erro ao conectar com a PokéAPI!")
                }
            })
    }
}