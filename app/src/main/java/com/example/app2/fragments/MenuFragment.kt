
package com.example.app2.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.app2.FavoritesActivity
import com.example.app2.PaginaPrincipal
import com.example.app2.R
import com.example.app2.PokemonListActivity

class MenuFragment : Fragment(R.layout.fragment_menu) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val botaoHome = view.findViewById<ImageButton>(R.id.botaoHome)
        val botaoFavoritos = view.findViewById<ImageButton>(R.id.botaoFavoritos)
        val botaoListaPokemons = view.findViewById<ImageButton>(R.id.botaoListaPokemons)

        if (requireActivity() is PaginaPrincipal) {
            botaoHome.setColorFilter(
                resources.getColor(R.color.yellow, null)
            )
        } else if (requireActivity() is FavoritesActivity) {
            botaoFavoritos.setColorFilter(
                resources.getColor(R.color.yellow, null)
            )
        } else if (requireActivity() is PokemonListActivity) {
            botaoListaPokemons.setColorFilter(
                resources.getColor(R.color.yellow, null)
            )
        }

        botaoHome.setOnClickListener {
            if (requireActivity() !is PaginaPrincipal) {
                val intent = Intent(requireContext(), PaginaPrincipal::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        botaoFavoritos.setOnClickListener {
            if (requireActivity() !is FavoritesActivity) {
                val intent = Intent(requireContext(), FavoritesActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        botaoListaPokemons.setOnClickListener {
            if (requireActivity() !is PokemonListActivity) {
                val intent = Intent(requireContext(), PokemonListActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }
}
