package com.example.app2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.app2.databinding.ActivityPokemonDetailsBinding

class PokemonDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailsBinding
    private lateinit var viewModel: PokemonDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[PokemonDetailsViewModel::class.java]

        val pokemonId = intent.getIntExtra("pokemon_id", -1)

        if (pokemonId == -1) {
            finish()
            return
        }

        observarPokemon()
        viewModel.carregarPokemon(pokemonId)
    }

    private fun observarPokemon() {

        viewModel.pokemon.observe(this) { pokemon ->

            binding.nomePokemon.text =
                pokemon.name.replaceFirstChar { it.uppercase() }

            binding.numeroPokemon.text =
                "#${pokemon.id.toString().padStart(3, '0')}"

            binding.alturaPokemon.text =
                "Altura: ${pokemon.height / 10.0} m"

            binding.pesoPokemon.text =
                "Peso: ${pokemon.weight / 10.0} kg"

            Glide.with(this)
                .load(pokemon.sprites.frontDefault)
                .into(binding.imagemPokemon)

            pokemon.stats.forEach { stat ->

                when (stat.stat.name) {
                    "hp" ->
                        binding.hpPokemon.text = "HP: ${stat.baseStat}"
                    "attack" ->
                        binding.ataquePokemon.text = "Ataque: ${stat.baseStat}"
                    "defense" ->
                        binding.defesaPokemon.text = "Defesa: ${stat.baseStat}"
                    "special-attack" ->
                        binding.ataqueEspecialPokemon.text = "Ataque especial: ${stat.baseStat}"
                    "special-defense" ->
                        binding.defesaEspecialPokemon.text = "Defesa especial: ${stat.baseStat}"
                    "speed" ->
                        binding.velocidadePokemon.text = "Velocidade: ${stat.baseStat}"
                }
            }
        }

        viewModel.erro.observe(this) { mensagem ->
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}