
package com.example.app2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.app2.auth.LoginActivity
import com.example.app2.databinding.ActivityPaginaPrincipalBinding
import com.google.firebase.auth.FirebaseAuth

class PaginaPrincipal : AppCompatActivity() {
    private lateinit var binding: ActivityPaginaPrincipalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val usuario = FirebaseAuth.getInstance().currentUser
        if (usuario == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
        super.onCreate(savedInstanceState)
        binding = ActivityPaginaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomNavigation()
    }

    private fun setBottomNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}
