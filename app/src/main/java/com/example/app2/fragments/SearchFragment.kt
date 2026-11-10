package com.example.app2.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.app2.R
import com.example.app2.PokemonDetailActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class SearchFragment : Fragment(R.layout.fragment_search) {
    private lateinit var viewModel: SearchViewModel

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        val input = view.findViewById<EditText>(R.id.campoPokemon)
        val botao = view.findViewById<Button>(R.id.botaoBuscar)

        botao.setOnClickListener {
            viewModel.buscar(input.text.toString())
        }

        viewModel.pokemon.observe(viewLifecycleOwner) { pokemon ->
            val intent = Intent(requireContext(), PokemonDetailActivity::class.java)

            intent.putExtra("pokemon_id", pokemon.id)
            intent.putExtra("pokemon_name", pokemon.name)
            intent.putExtra("pokemon_height", pokemon.height)
            intent.putExtra("pokemon_weight", pokemon.weight)
            intent.putExtra("pokemon_image", pokemon.sprites.front_default)
            intent.putExtra("pokemon_hp", pokemon.hp)
            intent.putExtra("pokemon_attack", pokemon.attack)
            intent.putExtra("pokemon_defense", pokemon.defense)
            intent.putExtra("pokemon_speed", pokemon.speed)

            startActivity(intent)
        }

        viewModel.erro.observe(viewLifecycleOwner) { mensagem ->
            Toast.makeText(
                requireContext(),
                mensagem,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
