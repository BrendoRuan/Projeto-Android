package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainListar : AppCompatActivity(){
    // Método chamado quando a Activity é criada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Define o layout XML que será usado nesta Activity
        setContentView(R.layout.activity_listar)
        // Certifique-se de criar o arquivo activity_listar.xml em res/layout

        // Referência para o botão "Aluno"
        val btnAluno: Button = findViewById(R.id.btnAluno)

        // Referência para o botão "Disciplina"
        val btnDisciplina: Button = findViewById(R.id.btnDisciplina)

        // Referência para o botão "Voltar"
        val btnVoltar: Button = findViewById(R.id.btnVoltar)

        // Configura ação ao clicar no botão "Aluno"
        btnAluno.setOnClickListener {
            // Cria uma Intent para abrir a Activity MainActivityListAluno
            val intent = Intent(this, MainActivityListAluno::class.java)
            // Inicia a Activity MainActivityListAluno
            startActivity(intent)
        }

        // Configura ação ao clicar no botão "Disciplina"
        btnDisciplina.setOnClickListener {
            // Cria uma Intent para abrir a Activity MainActivityListDisciplina
            val intent = Intent(this, MainActivityListDisciplina::class.java)
            // Inicia a Activity MainActivityListDisciplina
            startActivity(intent)
        }

        // Configura ação ao clicar no botão "Voltar"
        btnVoltar.setOnClickListener {
            // Cria uma Intent para abrir a Activity MainHome
            val intent = Intent(this, MainHome::class.java)
            // Inicia a Activity MainHome
            startActivity(intent)
        }
    }
}