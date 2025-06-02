package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivityListDisciplina : AppCompatActivity() {

    // Declaração dos componentes e do banco de dados
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var listDisciplina: ListView
    private lateinit var btnVoltar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_disciplina) // Define o layout da tela

        dbHelper = DatabaseHelper(this) // Inicializa o helper do banco SQLite

        // Liga os componentes do layout aos objetos do Kotlin
        listDisciplina = findViewById(R.id.listDisciplina)
        btnVoltar = findViewById(R.id.btnVoltar)

        // Configura o botão de voltar para a tela principal
        btnVoltar.setOnClickListener {
            val intent = Intent(this, MainHome::class.java)
            startActivity(intent)
        }

        // Exibe a lista de disciplinas
        displayListaDisciplinas()
    }

    // Função que busca e exibe disciplinas no ListView
    private fun displayListaDisciplinas() {
        val cursor = dbHelper.getAllDisciplinas() // Chama o novo método que retorna só disciplinas
        val disciplinasSet = mutableSetOf<String>() // Usamos Set para evitar duplicatas

        if (cursor.moveToFirst()) {
            do {
                // Pega a string da disciplina do banco
                val disciplina = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DISCIPLINA))

                // Adiciona no Set, evitando repetição
                disciplinasSet.add(disciplina)
            } while (cursor.moveToNext())
        }

        cursor.close()

        val disciplinasList = disciplinasSet.toList()

        // Se não tiver nenhuma disciplina cadastrada
        val finalList = if (disciplinasList.isEmpty()) {
            listOf("Nenhuma disciplina cadastrada.")
        } else {
            disciplinasList
        }

        // Cria o adaptador e define no ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, finalList)
        listDisciplina.adapter = adapter

        // Mostra a disciplina em um Toast ao clicar
        listDisciplina.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, finalList[position], Toast.LENGTH_SHORT).show()
        }
    }
}