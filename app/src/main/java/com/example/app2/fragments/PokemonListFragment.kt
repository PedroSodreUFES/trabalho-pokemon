package com.example.app2.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.R
import com.example.app2.adapters.PokemonListaAdapter
import com.example.app2.viewmodel.PokemonListViewModel

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

        recyclerPokemons.layoutManager =
            LinearLayoutManager(requireContext())

        recyclerPokemons.adapter = adapter

        viewModel.pokemons.observe(viewLifecycleOwner) { pokemons ->
            adapter.atualizarLista(pokemons)
        }

        viewModel.erro.observe(viewLifecycleOwner) { mensagem ->
            if (!mensagem.isNullOrBlank()) {
                android.widget.Toast.makeText(
                    requireContext(),
                    mensagem,
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }
        }

        botaoMais.setOnClickListener {
            viewModel.aumentarQuantidade()
        }

        if (savedInstanceState == null) {
            viewModel.carregarPokemons()
        }
    }
}