
package com.example.app2.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.R
import com.example.app2.adapters.PokemonListaAdapter
import com.example.app2.viewmodel.PokemonListViewModel
import com.google.android.material.snackbar.Snackbar

class PokemonListFragment : Fragment(R.layout.fragment_pokemon_list) {
    private lateinit var viewModel: PokemonListViewModel
    private lateinit var adapter: PokemonListaAdapter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[PokemonListViewModel::class.java]

        val recyclerPokemons =
            view.findViewById<RecyclerView>(R.id.recyclerPokemons)

        val botaoMais =
            view.findViewById<Button>(R.id.botaoMais)

        adapter = PokemonListaAdapter(mutableListOf())

        // Grid com 3 colunas
        recyclerPokemons.layoutManager =
            GridLayoutManager(requireContext(), 3)

        recyclerPokemons.adapter = adapter

        viewModel.pokemons.observe(viewLifecycleOwner) { pokemons ->
            adapter.atualizarLista(pokemons)
        }

        viewModel.erro.observe(viewLifecycleOwner) { mensagem ->
            if (!mensagem.isNullOrBlank()) {
                Snackbar.make(
                    view,
                    mensagem,
                    Snackbar.LENGTH_LONG
                ).setBackgroundTint(Color.rgb(183, 28, 28))
                    .setTextColor(Color.WHITE)
                    .show()
            }
        }

        botaoMais.setOnClickListener {
            viewModel.aumentarQuantidade()

            Snackbar.make(
                view,
                "Mais 12 Pokémons foram carregados!\nRole para baixo para visualizar os novos Pokémons.",
                Snackbar.LENGTH_LONG
            ).setBackgroundTint(Color.rgb(27, 94, 32))
                .setTextColor(Color.WHITE)
                .show()
        }

        if (savedInstanceState == null) {
            viewModel.carregarPokemons()
        }
    }
}
