package com.example.myapplication // Define o pacote da aplicação

// Importações necessárias para funcionalidades usadas na Activity
import android.annotation.SuppressLint
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("CutPasteId") // Suprime alerta de cópia/recorte de IDs no Android Studio
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inicio) // Define o layout XML usado por essa Activity (inicio.xml)

        // Referencia os componentes do layout pelo ID
        val editUsername: EditText = findViewById(R.id.editUsername) // Campo de texto para o nome de usuário
        val editPassword: EditText = findViewById(R.id.editPassword) // Campo de texto para a senha
        val txtCadastro: TextView = findViewById(R.id.txtForgetYourPassword) // Texto clicável para cadastro (nome está genérico)
        val txtTitle: TextView = findViewById(R.id.txtTitle) // Título da tela
        val btnLogin: Button = findViewById(R.id.btnLogin) // Botão de login
        val checkBoxRemember: CheckBox = findViewById(R.id.checkBoxRemember) // Checkbox "Lembrar-me"

        // Ação para o texto "Cadastre-se" (abre a tela de cadastro)
        txtCadastro.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        // Ação para o botão de login
        btnLogin.setOnClickListener {
            // Verifica se os campos estão vazios
            if (editUsername.text.isEmpty() || editPassword.text.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show()
            } else {
                // Verifica se o checkbox "lembrar-me" está marcado
                val lembrar = checkBoxRemember.isChecked
                if (lembrar) {
                    Toast.makeText(this, "Lembrar login ativado", Toast.LENGTH_SHORT).show()
                }

                // Simula um processo de login com `Thread` (ex: validação com servidor)
                Toast.makeText(this, "Fazendo login...", Toast.LENGTH_SHORT).show()
                Thread {
                    Thread.sleep(2000) // Simula tempo de espera (2 segundos)
                    runOnUiThread {
                        // Após a simulação, navega para a tela principal
                        val intent = Intent(this, MainHome::class.java)
                        startActivity(intent)
                    }
                }.start()
            }
        }

        // Validação ao perder o foco do campo de nome de usuário
        editUsername.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && editUsername.text.isEmpty()) {
                editUsername.error = "O campo de username não pode estar vazio!"
            }
        }

        // Validação ao perder o foco do campo de senha
        editPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && editPassword.text.isEmpty()) {
                editPassword.error = "O campo de senha não pode estar vazio!"
            }
        }

        // Alerta com caixa de diálogo ao clicar no título da tela
        txtTitle.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
                .setTitle("Bem-vindo!") // Título da caixa de diálogo
                .setMessage("Deseja continuar com o login?") // Mensagem
                .setPositiveButton("Sim") { _, _ -> // Botão "Sim"
                    Toast.makeText(this, "Você clicou em Sim", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Não") { _, _ -> // Botão "Não"
                    Toast.makeText(this, "Você clicou em Não", Toast.LENGTH_SHORT).show()
                }
                .create()
            dialog.show() // Exibe a caixa de diálogo
        }
    }
}
