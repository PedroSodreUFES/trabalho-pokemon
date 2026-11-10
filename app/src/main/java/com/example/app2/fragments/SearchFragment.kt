package com.example.app2.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.app2.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.app2.PokemonDetailsActivity

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
        val resultado = view.findViewById<TextView>(R.id.resultado)

        botao.setOnClickListener {
            viewModel.buscar(input.text.toString())
        }

        viewModel.resultado.observe(viewLifecycleOwner) {
            resultado.text = it
        }

        viewModel.pokemon.observe(viewLifecycleOwner) { pokemon ->
            val intent = Intent(
                requireContext(),
                PokemonDetailsActivity::class.java
            )
            intent.putExtra("pokemon_id", pokemon.id)
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