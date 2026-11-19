
package com.example.app2.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.app2.PaginaPrincipal
import com.example.app2.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import android.widget.TextView

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var edtEmail: TextInputEditText
    private lateinit var edtSenha: TextInputEditText
    private lateinit var btnEntrar: Button
    private lateinit var txtCriarConta: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edtEmail)
        edtSenha = findViewById(R.id.edtSenha)
        btnEntrar = findViewById(R.id.btnEntrar)
        txtCriarConta = findViewById(R.id.txtCriarConta)

        btnEntrar.setOnClickListener {
            realizarLogin()
        }
        txtCriarConta.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun realizarLogin() {
        val email = edtEmail.text.toString().trim()
        val senha = edtSenha.text.toString()
        if (email.isEmpty() || senha.isEmpty()) {
            Snackbar.make(
                findViewById(R.id.btnEntrar),
                "Preencha todos os campos!",
                Snackbar.LENGTH_SHORT
            ).show()
            return
        }

        btnEntrar.isEnabled = false
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                btnEntrar.isEnabled = true
                if (task.isSuccessful) {
                    Snackbar.make(
                        findViewById(R.id.btnEntrar),
                        "Login realizado com sucesso!",
                        Snackbar.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this, PaginaPrincipal::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Snackbar.make(
                        findViewById(R.id.btnEntrar),
                        "Credenciais inválidas.",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
    }
}
