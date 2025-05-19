package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivityListDisciplina : AppCompatActivity() {

    // Método que é executado quando a activity é criada
    override fun onCreate(savedInstanceState: Bundle?) { // Corrigido: 'savedInstanceState'
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_disciplina) // Define o layout da tela

        // Referência para o ListView onde serão exibidas as disciplinas
        val listDisciplina = findViewById<ListView>(R.id.listDisciplina)

        // Referência para o botão "Voltar"
        val btnVoltar: Button = findViewById(R.id.btnVoltar)

        // Ação para o botão "Voltar"
        btnVoltar.setOnClickListener {
            // Cria uma Intent para abrir a tela de listagem principal
            val intent = Intent(this, MainListar::class.java)
            startActivity(intent)
        }

        // Lista de exemplo com valores fixos para exibição no ListView
        val arrayList = arrayOf(
            "android",
            "limao",
            "android",
            "limao",
            "android",
            "limao",
            "android",
            "limao",
            "android",
            "limao",
            "android",
            "limao"
        )

        // Adapter que conecta a lista de dados com o ListView
        listDisciplina.adapter = ArrayAdapter(
            this, // Contexto da activity
            android.R.layout.simple_list_item_1, // Layout padrão para cada item da lista
            arrayList // Dados a serem exibidos
        )

        // Evento de clique em um item da lista
        listDisciplina.setOnItemClickListener { adapterView, view, position, id ->
            // Mostra uma mensagem com o item clicado
            Toast.makeText(this, arrayList[position], Toast.LENGTH_SHORT).show()
        }
    }
}
