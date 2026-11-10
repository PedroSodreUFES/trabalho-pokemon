package com.example.app2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.app2.databinding.ActivityPaginaPrincipalBinding
import com.example.app2.fragments.MenuFragment
import com.example.app2.fragments.SearchFragment

class PaginaPrincipal : AppCompatActivity() {
    private lateinit var binding: ActivityPaginaPrincipalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaginaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.menuContainer, MenuFragment())
                .replace(R.id.buscaContainer, SearchFragment())
                .commit()
        }
    }
}