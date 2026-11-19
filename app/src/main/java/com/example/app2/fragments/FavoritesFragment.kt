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
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import android.graphics.Color


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
            AlertDialog.Builder(requireContext())
                .setTitle("Remover dos favoritos?")
                .setMessage("Tem certeza de que deseja remover este Pokémon dos seus favoritos?")
                .setNegativeButton("Cancelar") { dialog, _ ->
                    Snackbar.make(
                        requireView(),
                        "Ação cancelada",
                        Snackbar.LENGTH_SHORT
                    ).setBackgroundTint(Color.WHITE)
                        .setTextColor(Color.BLACK)
                        .show()
                    dialog.dismiss()
                }
                .setPositiveButton("Continuar") { _, _ ->
                    viewModel.remover(pokemonId)
                    Snackbar.make(
                        requireView(),
                        "Pokémon desfavoritado!",
                        Snackbar.LENGTH_SHORT
                    ).setBackgroundTint(Color.RED)
                        .setTextColor(Color.WHITE)
                        .show()
                }
                .show()
        }

        recyclerFavoritos.layoutManager = LinearLayoutManager(requireContext())
        recyclerFavoritos.adapter = adapter

        viewModel.listarFavoritos().observe(viewLifecycleOwner) { favoritos ->
            adapter.atualizarLista(favoritos)
        }
    }
}