package com.example.app2.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.R
import com.example.app2.adapters.PokemonFavoritoAdapter
import com.example.app2.viewmodel.PokemonViewModel

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private lateinit var viewModel: PokemonViewModel
    private lateinit var adapter: PokemonFavoritoAdapter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[PokemonViewModel::class.java]
        val recyclerFavoritos = view.findViewById<RecyclerView>(R.id.recyclerFavoritos)

        adapter = PokemonFavoritoAdapter(mutableListOf()) { pokemonId ->
            viewModel.remover(pokemonId)
        }

        recyclerFavoritos.layoutManager = LinearLayoutManager(requireContext())
        recyclerFavoritos.adapter = adapter

        viewModel.listarFavoritos().observe(viewLifecycleOwner) { favoritos ->
            adapter.atualizarLista(favoritos)
        }
    }
}