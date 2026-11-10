package com.example.app2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app2.R
import com.example.app2.models.PokemonListItem

class PokemonListaAdapter(
    private val listaPokemons: MutableList<PokemonListItem>
) : RecyclerView.Adapter<PokemonListaAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val imagem: ImageView = itemView.findViewById(R.id.imagemPokemonLista)

        val nome: TextView = itemView.findViewById(R.id.nomePokemonLista)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon_lista, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: PokemonViewHolder,
        position: Int
    ) {
        val pokemon = listaPokemons[position]
        holder.nome.text = pokemon.name.replaceFirstChar { it.uppercase() }

        val pokemonId = pokemon.url
            .trimEnd('/')
            .substringAfterLast('/')

        val imagemUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"

        Glide.with(holder.itemView.context)
            .load(imagemUrl)
            .into(holder.imagem)
    }

    override fun getItemCount(): Int {
        return listaPokemons.size
    }

    fun atualizarLista(novaLista: List<PokemonListItem>) {
        listaPokemons.clear()
        listaPokemons.addAll(novaLista)
        notifyDataSetChanged()
    }
}