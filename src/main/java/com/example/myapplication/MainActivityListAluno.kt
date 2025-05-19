package com.example.myapplication // Pacote da aplicação

// Importações necessárias
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivityListAluno : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { // ⛔️ ERRO AQUI
        // Correto seria: onCreate(savedInstanceState: Bundle?)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_aluno) // Define o layout da tela

        // Referência para a ListView e botão "Voltar"
        val listAluno = findViewById<ListView>(R.id.listAluno)
        val btnVoltar: Button = findViewById(R.id.btnVoltar)

        // Ação do botão "Voltar": volta para a tela MainListar
        btnVoltar.setOnClickListener {
            val intent = Intent(this, MainListar::class.java)
            startActivity(intent)
        }

        // Lista de exemplo que será exibida na ListView
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

        // Adaptador para exibir a lista
        listAluno.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, // Layout padrão de item
            arrayList
        )

        // Ação ao clicar em um item da lista
        listAluno.setOnItemClickListener { adapterView, view, position, id ->
            Toast.makeText(this, arrayList[position], Toast.LENGTH_SHORT).show()
        }
    }
}
