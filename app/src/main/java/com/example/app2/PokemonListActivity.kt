package com.example.app2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.app2.fragments.MenuFragment
import com.example.app2.fragments.PokemonListFragment

class PokemonListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_pokemon_list)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.menuContainer, MenuFragment())
                .replace(R.id.listaPokemonsContainer, PokemonListFragment())
                .commit()
        }
    }
}