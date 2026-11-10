package com.example.app2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app2.api.RetrofitClient
import com.example.app2.models.PokemonListItem
import com.example.app2.models.PokemonListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonListViewModel : ViewModel() {
    private val _pokemons = MutableLiveData<List<PokemonListItem>>()
    val pokemons: LiveData<List<PokemonListItem>> = _pokemons
    private val _erro = MutableLiveData<String>()
    val erro: LiveData<String> = _erro
    private val quantidadePorCarregamento = 6
    private var offset = 0
    private var carregando = false

    fun carregarPokemons() {
        if (carregando) return
        carregando = true
        RetrofitClient.pokemonService
            .listarPokemons(quantidadePorCarregamento, offset)
            .enqueue(object : Callback<PokemonListResponse> {
                override fun onResponse(
                    call: Call<PokemonListResponse>,
                    response: Response<PokemonListResponse>
                ) {
                    carregando = false

                    if (response.isSuccessful) {
                        val resultado = response.body()
                        if (resultado != null) {
                            val listaAtual = _pokemons.value.orEmpty()
                            _pokemons.value = listaAtual + resultado.results
                            offset += resultado.results.size
                        } else {
                            _erro.value = "Resposta vazia da PokéAPI!"
                        }
                    } else {
                        _erro.value = "Erro ao carregar Pokémon!"
                    }
                }

                override fun onFailure(
                    call: Call<PokemonListResponse>,
                    t: Throwable
                ) {
                    carregando = false
                    _erro.postValue("Erro ao conectar com a PokéAPI!")
                }
            })
    }

    fun aumentarQuantidade() {
        carregarPokemons()
    }
}