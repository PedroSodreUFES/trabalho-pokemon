package com.example.app2.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.app2.R
import com.example.app2.PokemonDetailActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import android.graphics.Color
import com.google.firebase.auth.FirebaseAuth
import com.example.app2.auth.LoginActivity

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
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        botao.setOnClickListener {
            viewModel.buscar(input.text.toString())
        }

        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            requireActivity().finish()
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
            Snackbar.make(
                requireView(),
                mensagem,
                Snackbar.LENGTH_SHORT
            ).setBackgroundTint(Color.rgb(183, 28, 28))
                .setTextColor(Color.WHITE)
                .show()
        }
    }
}
