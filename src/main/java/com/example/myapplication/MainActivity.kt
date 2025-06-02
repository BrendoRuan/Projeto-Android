package com.example.myapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inicio)

        // Referência aos componentes da interface
        val editEmail: EditText = findViewById(R.id.editEmail)
        val editPassword: EditText = findViewById(R.id.editPassword)
        val txtCadastro: TextView = findViewById(R.id.txtForgetYourPassword)
        val txtTitle: TextView = findViewById(R.id.txtTitle)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val checkBoxRemember: CheckBox = findViewById(R.id.checkBoxRemember)

        // Abre a tela de cadastro ao clicar no texto
        txtCadastro.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        // Ação do botão de login
        btnLogin.setOnClickListener {
            val emailInput = editEmail.text.toString().trim()
            val passwordInput = editPassword.text.toString().trim()

            // Verifica se os campos estão vazios
            if (emailInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validação básica de formato de e-mail
            if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                Toast.makeText(this, "Por favor, insira um email válido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Abertura do SharedPreferences (modo privado, só acessível pela própria app)
            val sharedPref = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE)

            // Recupera email e senha salvos (se existirem)
            val savedEmail = sharedPref.getString("email", null)
            val savedPassword = sharedPref.getString("password", null)

            // Compara os dados inseridos com os salvos
            if (emailInput == savedEmail && passwordInput == savedPassword) {

                // Se a checkbox "Lembrar" estiver marcada, mantém os dados salvos
                if (checkBoxRemember.isChecked) {
                    // Edita e salva os dados no SharedPreferences
                    val editor = sharedPref.edit()
                    editor.putString("email", emailInput)     // Salva o e-mail
                    editor.putString("password", passwordInput) // Salva a senha
                    editor.apply() // Aplica as mudanças (commit assíncrono)
                    Toast.makeText(this, "Login salvo com sucesso.", Toast.LENGTH_SHORT).show()
                }

                // Login bem-sucedido
                Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainHome::class.java))
                finish()

            } else {
                // Dados incorretos
                Toast.makeText(this, "Email ou senha incorretos!", Toast.LENGTH_SHORT).show()
            }
        }

        // Validação ao sair do campo de email
        editEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && editEmail.text.isEmpty()) {
                editEmail.error = "O campo de email não pode estar vazio!"
            }
        }

        // Validação ao sair do campo de senha
        editPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && editPassword.text.isEmpty()) {
                editPassword.error = "O campo de senha não pode estar vazio!"
            }
        }

        // Clique no título mostra um diálogo de confirmação
        txtTitle.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Bem-vindo!")
                .setMessage("Deseja continuar com o login?")
                .setPositiveButton("Sim") { _, _ ->
                    Toast.makeText(this, "Você clicou em Sim", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Não") { _, _ ->
                    Toast.makeText(this, "Você clicou em Não", Toast.LENGTH_SHORT).show()
                }
                .create()
                .show()
        }
    }
}
