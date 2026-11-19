package com.example.app2

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.app2.auth.LoginActivity
import com.example.app2.fragments.MenuFragment
import com.example.app2.fragments.PokemonListFragment
import com.google.firebase.auth.FirebaseAuth

class PokemonListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val usuario = FirebaseAuth.getInstance().currentUser
        if (usuario == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

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