package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainHome : AppCompatActivity() {

    // Adição Menu-Home:
    // - novos botões adicionados numa unica tela
    // (cadastarAluno/CadastrarDisciplina/ListarAluno/ListarDisciplina)

    // Método chamado quando a Activity é criada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Define o layout XML que será usado nesta Activity
        setContentView(R.layout.activity_main_activityhome)
        // Certifique-se de ter criado o arquivo activity_main_activityhome.xml em res/layout

        // Referência para o botão de cadastro, busca pelo ID definido no XML
        val btnCadastrarAluno: Button = findViewById(R.id.btnCadastrarAluno)

        //
        val btnCadastrarDisciplina: Button = findViewById(R.id.btnCadastrarDisciplina)

        // Referência para o botão de listar registros
        val btnListarAluno: Button = findViewById(R.id.btnListarAluno)

        //
        val btnListarDisciplina: Button = findViewById(R.id.btnListarDisciplina)

        // Referência para o botão de voltar para a tela anterior
        val btnSair: Button = findViewById(R.id.btnSair)


        // Configura ação ao clicar no botão "Cadastra Aluno"
        btnCadastrarAluno.setOnClickListener {
            // Cria uma Intent para abrir a Activity MainCadastrarAluno
            val intent = Intent(this, MainCadastroAluno::class.java)
            // Inicia a Activity MainCadastrarAluno
            startActivity(intent)
        }

        // Configura ação ao clicar no botão "Cadastra Aluno"
        btnCadastrarDisciplina.setOnClickListener {
            // Cria uma Intent para abrir a Activity MainCadastrarDisciplina
            val intent = Intent(this, MainCadastroDisciplina::class.java)
            // Inicia a Activity MainCadastrarDisciplina
            startActivity(intent)
        }

        // Configura ação ao clicar no botão "Lista de Alunos"
        btnListarAluno.setOnClickListener {
            // Cria uma Intent para abrir a Activity MainListar
            val intent = Intent(this, MainActivityListAluno::class.java)
            // Inicia a Activity MainListar
            startActivity(intent)
        }

        // Configura ação ao clicar no botão "Lista de Disciplina"
        btnListarDisciplina.setOnClickListener {
            // Cria uma Intent para abrir a Activity MainListarDisciplina
            val intent = Intent(this,MainActivityListDisciplina::class.java)
            // Inicia a Activity MainListarDisciplina
            startActivity(intent)
        }

        // Configura ação ao clicar no botão "Sair"
        btnSair.setOnClickListener {
            // Cria uma Intent para abrir a Activity MainActivity (tela principal)
            val intent = Intent(this, MainActivity::class.java)
            // Inicia a Activity MainActivity
            startActivity(intent)
        }
    }
}
