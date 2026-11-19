
package com.example.app2.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app2.R
import com.example.app2.PokemonDetailActivity
import com.example.app2.api.RetrofitClient
import com.example.app2.models.Pokemon
import com.example.app2.models.PokemonListItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        val imagemUrl =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"

        Glide.with(holder.itemView.context)
            .load(imagemUrl)
            .into(holder.imagem)

        // Ao clicar no card, busca os dados completos do Pokémon
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            holder.itemView.isEnabled = false

            RetrofitClient.pokemonService
                .buscarPokemon(pokemon.name)
                .enqueue(object : Callback<Pokemon> {
                    override fun onResponse(
                        call: Call<Pokemon>,
                        response: Response<Pokemon>
                    ) {
                        holder.itemView.isEnabled = true
                        if (response.isSuccessful) {
                            val pokemonCompleto = response.body()
                            if (pokemonCompleto != null) {
                                val intent = Intent(
                                    context,
                                    PokemonDetailActivity::class.java
                                ).apply {
                                    putExtra(
                                        "pokemon_id",
                                        pokemonCompleto.id
                                    )
                                    putExtra(
                                        "pokemon_name",
                                        pokemonCompleto.name
                                    )
                                    putExtra(
                                        "pokemon_height",
                                        pokemonCompleto.height
                                    )
                                    putExtra(
                                        "pokemon_weight",
                                        pokemonCompleto.weight
                                    )
                                    putExtra(
                                        "pokemon_image",
                                        pokemonCompleto.sprites.front_default
                                    )
                                    putExtra(
                                        "pokemon_hp",
                                        pokemonCompleto.hp
                                    )
                                    putExtra(
                                        "pokemon_attack",
                                        pokemonCompleto.attack
                                    )
                                    putExtra(
                                        "pokemon_defense",
                                        pokemonCompleto.defense
                                    )
                                    putExtra(
                                        "pokemon_speed",
                                        pokemonCompleto.speed
                                    )
                                }
                                context.startActivity(intent)
                            }
                        } else {
                            Snackbar.make(
                                holder.itemView,
                                "Não foi possível carregar o Pokémon.",
                                Snackbar.LENGTH_SHORT
                            ).setBackgroundTint(android.graphics.Color.rgb(183, 28, 28))
                                .setTextColor(android.graphics.Color.WHITE)
                                .show()
                        }
                    }

                    override fun onFailure(
                        call: Call<Pokemon>,
                        t: Throwable
                    ) {
                        holder.itemView.isEnabled = true
                        Snackbar.make(
                            holder.itemView,
                            "Erro ao buscar Pokémon: ${t.message}",
                            Snackbar.LENGTH_LONG
                        ).setBackgroundTint(android.graphics.Color.rgb(183, 28, 28))
                            .setTextColor(android.graphics.Color.WHITE)
                            .show()
                    }
                })
        }
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
