package com.example.app2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.app2.fragments.FavoritesFragment
import com.example.app2.fragments.MenuFragment

class FavoritesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
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
