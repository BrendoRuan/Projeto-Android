package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

// Declaração da Activity MainHome que herda de AppCompatActivity
// Essa Activity funciona como um menu principal, com botões para navegar para outras telas do app
class MainHome : AppCompatActivity() {

    // Comentário explicativo sobre a função da tela MainHome:
    // Aqui são adicionados vários botões numa única tela para navegar para cadastro/listagem

    // Método chamado quando a Activity é criada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Define o layout XML a ser usado para essa Activity
        // O arquivo deve estar em res/layout/activity_main_activityhome.xml
        setContentView(R.layout.activity_main_activityhome)

        // Obtém a referência ao botão "Cadastrar Aluno" do layout pelo seu ID
        val btnCadastrarAluno: Button = findViewById(R.id.btnCadastrarAluno)

        // Obtém a referência ao botão "Cadastrar Disciplina" pelo ID
        val btnCadastrarDisciplina: Button = findViewById(R.id.btnCadastrarDisciplina)

        // Obtém a referência ao botão "Listar Aluno" pelo ID
        val btnListarAluno: Button = findViewById(R.id.btnListarAluno)

        // Obtém a referência ao botão "Listar Disciplina" pelo ID
        val btnListarDisciplina: Button = findViewById(R.id.btnListarDisciplina)

        // Obtém a referência ao botão "Sair" (voltar à tela principal) pelo ID
        val btnSair: Button = findViewById(R.id.btnSair)


        // Configura o comportamento ao clicar no botão "Cadastrar Aluno"
        btnCadastrarAluno.setOnClickListener {
            // Cria uma Intent para abrir a Activity MainCadastroAluno
            val intent = Intent(this, MainCadastroAluno::class.java)
            // Inicia a Activity especificada pela Intent
            startActivity(intent)
        }

        // Configura o comportamento ao clicar no botão "Cadastrar Disciplina"
        btnCadastrarDisciplina.setOnClickListener {
            // Cria uma Intent para abrir a Activity MainCadastroDisciplina
            val intent = Intent(this, MainCadastroDisciplina::class.java)
            // Inicia a Activity para cadastro de disciplinas
            startActivity(intent)
        }

        // Configura o comportamento ao clicar no botão "Listar Aluno"
        btnListarAluno.setOnClickListener {
            // Cria uma Intent para abrir a Activity MainActivityListAluno (listagem dos alunos)
            val intent = Intent(this, MainActivityListAluno::class.java)
            // Inicia a Activity que mostra a lista de alunos
            startActivity(intent)
        }

        // Configura o comportamento ao clicar no botão "Listar Disciplina"
        btnListarDisciplina.setOnClickListener {
            // Cria uma Intent para abrir a Activity MainActivityListDisciplina (lista de disciplinas)
            val intent = Intent(this, MainActivityListDisciplina::class.java)
            // Inicia a Activity que mostra a lista de disciplinas
            startActivity(intent)
        }

        // Configura o comportamento ao clicar no botão "Sair"
        btnSair.setOnClickListener {
            // Cria uma Intent para abrir a Activity MainActivity, que seria a tela principal do app
            val intent = Intent(this, MainActivity::class.java)
            // Inicia a Activity principal
            startActivity(intent)
        }
    }
}
