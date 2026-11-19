
package com.example.app2

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.app2.models.PokemonEntity
import com.example.app2.viewmodel.PokemonViewModel
import android.content.Intent
import android.widget.ImageButton
import com.google.android.material.snackbar.Snackbar
import android.graphics.Color
import com.example.app2.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class PokemonDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: PokemonViewModel
    private var favoritado: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val usuario = FirebaseAuth.getInstance().currentUser
        if (usuario == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)

        val imagem = findViewById<ImageView>(R.id.pokemonImage)
        val nome = findViewById<TextView>(R.id.pokemonName)
        val id = findViewById<TextView>(R.id.pokemonId)
        val altura = findViewById<TextView>(R.id.pokemonHeight)
        val peso = findViewById<TextView>(R.id.pokemonWeight)
        val hp = findViewById<TextView>(R.id.pokemonHp)
        val attack = findViewById<TextView>(R.id.pokemonAttack)
        val defense = findViewById<TextView>(R.id.pokemonDefense)
        val speed = findViewById<TextView>(R.id.pokemonSpeed)
        val botaoFavoritar = findViewById<Button>(R.id.botaoFavoritar)
        val botaoVoltar = findViewById<ImageButton>(R.id.botaoVoltar)

        val pokemonNome = intent.getStringExtra("pokemon_name")
        val pokemonId = intent.getIntExtra("pokemon_id", 0)
        val pokemonAltura = intent.getIntExtra("pokemon_height", 0)
        val pokemonPeso = intent.getIntExtra("pokemon_weight", 0)
        val pokemonImagem = intent.getStringExtra("pokemon_image")
        val pokemonHp = intent.getIntExtra("pokemon_hp", 0)
        val pokemonAttack = intent.getIntExtra("pokemon_attack", 0)
        val pokemonDefense = intent.getIntExtra("pokemon_defense", 0)
        val pokemonSpeed = intent.getIntExtra("pokemon_speed", 0)

        nome.text = pokemonNome?.replaceFirstChar { it.uppercase() }
        id.text = "ID $pokemonId"
        altura.text = "Altura: ${pokemonAltura / 10.0} m"
        peso.text = "Peso: ${pokemonPeso / 10.0} kg"
        hp.text = pokemonHp.toString()
        attack.text = pokemonAttack.toString()
        defense.text = pokemonDefense.toString()
        speed.text = pokemonSpeed.toString()

        Glide.with(this)
            .load(pokemonImagem)
            .into(imagem)

        viewModel = ViewModelProvider(this)[PokemonViewModel::class.java]

        // Verifica se o Pokémon já está favoritado
        botaoFavoritar.isEnabled = false
        viewModel.estaFavoritado(pokemonId).observe(this) { estaFavoritado ->
            favoritado = estaFavoritado
            botaoFavoritar.text = if (favoritado == true) {
                "Desfavoritar"
            } else {
                "Favoritar"
            }
            botaoFavoritar.isEnabled = true
        }

        // Favoritar ou desfavoritar
        botaoFavoritar.setOnClickListener {
            if (favoritado == true) {
                viewModel.remover(pokemonId)
                Snackbar.make(
                    botaoFavoritar,
                    "Pokémon desfavoritado",
                    Snackbar.LENGTH_SHORT
                ).setBackgroundTint(Color.RED)
                    .setTextColor(Color.WHITE)
                    .show()
            } else {
                val pokemon = PokemonEntity(
                    id = pokemonId,
                    name = pokemonNome.orEmpty(),
                    height = pokemonAltura,
                    weight = pokemonPeso,
                    image = pokemonImagem,
                    hp = pokemonHp,
                    attack = pokemonAttack,
                    defense = pokemonDefense,
                    speed = pokemonSpeed
                )
                viewModel.inserir(pokemon)
                Snackbar.make(
                    botaoFavoritar,
                    "Pokemon favoritado",
                    Snackbar.LENGTH_SHORT
                ).setBackgroundTint(Color.rgb(46, 125, 50))
                    .setTextColor(Color.WHITE)
                    .show()
            }
            botaoFavoritar.isEnabled = false
        }

        botaoVoltar.setOnClickListener {
            val intent = Intent(this, PaginaPrincipal::class.java)
            startActivity(intent)
            finish()
        }
    }
}
