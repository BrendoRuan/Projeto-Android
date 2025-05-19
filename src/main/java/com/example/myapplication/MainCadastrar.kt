package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainCadastrar : AppCompatActivity() {

    // Método executado ao iniciar a Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar) // Define o layout da tela

        // Referência aos botões no layout
        val btnAluno: Button = findViewById(R.id.btnAluno) // Botão para acessar a lista de alunos
        val btnDisciplina: Button = findViewById(R.id.btnDisciplina) // Botão para acessar a lista de disciplinas
        val btnVoltar: Button = findViewById(R.id.btnVoltar) // Botão para voltar à tela inicial

        // Ação ao clicar no botão "Aluno"
        btnAluno.setOnClickListener {
            // Abre a tela de listagem de alunos
            val intent = Intent(this, MainActivityListAluno::class.java)
            startActivity(intent)
        }

        // Ação ao clicar no botão "Disciplina"
        btnDisciplina.setOnClickListener {
            // Abre a tela de listagem de disciplinas
            val intent = Intent(this, MainActivityListDisciplina::class.java)
            startActivity(intent)
        }

        // Ação ao clicar no botão "Voltar"
        btnVoltar.setOnClickListener {
            // Volta para a tela inicial (home)
            val intent = Intent(this, MainHome::class.java)
            startActivity(intent)
        }
    }
}
