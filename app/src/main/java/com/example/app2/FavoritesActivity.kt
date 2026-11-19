package com.example.app2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.app2.auth.LoginActivity
import com.example.app2.fragments.FavoritesFragment
import com.example.app2.fragments.MenuFragment
import com.google.firebase.auth.FirebaseAuth

class FavoritesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val usuario = FirebaseAuth.getInstance().currentUser
        if (usuario == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.menuContainer, MenuFragment())
                .replace(R.id.favoritosContainer, FavoritesFragment())
                .commit()
        }
    }
}
