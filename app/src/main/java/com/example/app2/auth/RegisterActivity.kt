
package com.example.app2.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.app2.PaginaPrincipal
import com.example.app2.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var edtNome: TextInputEditText
    private lateinit var edtEmail: TextInputEditText
    private lateinit var edtSenha: TextInputEditText
    private lateinit var edtConfirmarSenha: TextInputEditText

    private lateinit var btnCriarConta: Button
    private lateinit var btnVoltar: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        edtNome = findViewById(R.id.edtNome)
        edtEmail = findViewById(R.id.edtEmail)
        edtSenha = findViewById(R.id.edtSenha)
        edtConfirmarSenha = findViewById(R.id.edtConfirmarSenha)

        btnCriarConta = findViewById(R.id.btnCriarConta)
        btnVoltar = findViewById(R.id.btnVoltar)

        btnVoltar.setOnClickListener {
            finish()
        }

        btnCriarConta.setOnClickListener {
            realizarCadastro()
        }
    }

    private fun realizarCadastro() {

        val nome = edtNome.text.toString().trim()
        val email = edtEmail.text.toString().trim()
        val senha = edtSenha.text.toString()
        val confirmarSenha = edtConfirmarSenha.text.toString()

        if (nome.isEmpty() || email.isEmpty() ||
            senha.isEmpty() || confirmarSenha.isEmpty()
        ) {
            mostrarMensagem("Preencha todos os campos!")
            return
        }

        if (senha != confirmarSenha) {
            mostrarMensagem("As senhas não coincidem!")
            return
        }

        if (senha.length < 6) {
            mostrarMensagem("A senha deve ter pelo menos 6 caracteres!")
            return
        }
        btnCriarConta.isEnabled = false

        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val perfil = userProfileChangeRequest {
                        displayName = nome
                    }
                    auth.currentUser?.updateProfile(perfil)
                        ?.addOnCompleteListener {
                            mostrarMensagem("Conta criada com sucesso!")
                            startActivity(
                                Intent(this, PaginaPrincipal::class.java)
                            )
                            finish()
                        }

                } else {
                    btnCriarConta.isEnabled = true
                    mostrarMensagem(
                        task.exception?.localizedMessage
                            ?: "Não foi possível criar a conta."
                    )
                }
            }
    }

    private fun mostrarMensagem(mensagem: String) {
        Snackbar.make(
            findViewById(android.R.id.content),
            mensagem,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
