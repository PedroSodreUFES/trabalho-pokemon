
package com.example.app2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.example.app2.auth.LoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler(Looper.getMainLooper()).postDelayed({
            val usuario = FirebaseAuth.getInstance().currentUser
            if (usuario != null) {
                // Usuário já autenticado
                startActivity(Intent(this, PaginaPrincipal::class.java))
            } else {
                // User precisa fazer login
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()

        }, 2000)
    }
}
