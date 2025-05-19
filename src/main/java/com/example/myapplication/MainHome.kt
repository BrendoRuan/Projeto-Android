package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainHome : AppCompatActivity() {

    // Método chamado quando a Activity é criada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Define o layout XML que será usado nesta Activity
        setContentView(R.layout.activity_main_activityhome)
        // Certifique-se de ter criado o arquivo activity_main_activityhome.xml em res/layout

        // Referência para o botão de cadastro, busca pelo ID definido no XML
        val btnAdm: Button = findViewById(R.id.btnCadastro)

        // Referência para o botão de listar registros
        val btnListar: Button = findViewById(R.id.btnListar)

        // Referência para o botão de voltar para a tela anterior
        val btnVoltar: Button = findViewById(R.id.btnVoltar)

        // Configura ação ao clicar no botão "Listar"
        btnListar.setOnClickListener {
            // Cria uma Intent para abrir a Activity MainListar
            val intent = Intent(this, MainListar::class.java)
            // Inicia a Activity MainListar
            startActivity(intent)
        }

        // Configura ação ao clicar no botão "Cadastrar"
        btnAdm.setOnClickListener {
            // Cria uma Intent para abrir a Activity MainCadastrar
            val intent = Intent(this, MainCadastrar::class.java)
            // Inicia a Activity MainCadastrar
            startActivity(intent)
        }

        // Configura ação ao clicar no botão "Voltar"
        btnVoltar.setOnClickListener {
            // Cria uma Intent para abrir a Activity MainActivity (tela principal)
            val intent = Intent(this, MainActivity::class.java)
            // Inicia a Activity MainActivity
            startActivity(intent)
        }
    }
}
