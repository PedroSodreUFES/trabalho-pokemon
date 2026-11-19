package com.example.app2.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app2.R
import com.example.app2.PokemonDetailActivity
import com.example.app2.models.PokemonEntity

class PokemonFavoritoAdapter(
    private val listaPokemons: MutableList<PokemonEntity>,
    private val aoRemover: (Int) -> Unit
) : RecyclerView.Adapter<PokemonFavoritoAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagem: ImageView = itemView.findViewById(R.id.imagemPokemonFavorito)
        val nome: TextView = itemView.findViewById(R.id.nomePokemonFavorito)
        val botaoRemover: ImageButton =
            itemView.findViewById(R.id.botaoRemoverFavorito)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon_favorito, parent, false)

        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = listaPokemons[position]

        holder.nome.text = pokemon.name.replaceFirstChar { it.uppercase() }

        Glide.with(holder.itemView.context)
            .load(pokemon.image)
            .into(holder.imagem)

        // Clique no card: abre a página de detalhes
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context

            val intent = Intent(context, PokemonDetailActivity::class.java).apply {
                putExtra("pokemon_name", pokemon.name)
                putExtra("pokemon_id", pokemon.id)
                putExtra("pokemon_height", pokemon.height)
                putExtra("pokemon_weight", pokemon.weight)
                putExtra("pokemon_image", pokemon.image)
                putExtra("pokemon_hp", pokemon.hp)
                putExtra("pokemon_attack", pokemon.attack)
                putExtra("pokemon_defense", pokemon.defense)
                putExtra("pokemon_speed", pokemon.speed)
            }

            context.startActivity(intent)
        }

        // Clique no X: remove dos favoritos
        holder.botaoRemover.setOnClickListener {
            aoRemover(pokemon.id)
        }
    }

    override fun getItemCount(): Int = listaPokemons.size

    fun atualizarLista(novaLista: List<PokemonEntity>) {
        listaPokemons.clear()
        listaPokemons.addAll(novaLista)
        notifyDataSetChanged()
    }
}
