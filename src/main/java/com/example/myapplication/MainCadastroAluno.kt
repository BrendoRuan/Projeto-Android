package com.example.myapplication

import android.content.Intent
import android.database.SQLException
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// Activity responsável pelo cadastro, atualização, deleção e listagem de alunos
class MainCadastroAluno : AppCompatActivity() {

    // Declaração dos componentes visuais da interface e do helper para banco de dados
    private lateinit var dbHelper: DatabaseHelper        // Helper que gerencia o banco SQLite
    private lateinit var editTextId: EditText            // Campo para inserir/mostrar ID
    private lateinit var editTextNome: EditText          // Campo para inserir nome do aluno
    private lateinit var editTextDisciplina: EditText    // Campo para inserir nome da disciplina
    private lateinit var listView: ListView               // Lista para exibir cadastros
    private lateinit var buttonInsert: Button             // Botão para inserir novo cadastro
    private lateinit var buttonUpdate: Button             // Botão para atualizar cadastro existente
    private lateinit var buttonDelete: Button             // Botão para deletar cadastro
    private lateinit var buttonListar: Button             // Botão para abrir outra Activity de listagem

    // Método chamado na criação da Activity (ponto inicial)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Define o layout XML que será usado nesta tela
        setContentView(R.layout.activity_cadastro_aluno)

        // Instancia o helper do banco passando o contexto atual
        dbHelper = DatabaseHelper(this)

        // Liga as variáveis declaradas aos componentes visuais definidos no XML pelo ID
        editTextId = findViewById(R.id.editTextId)
        editTextNome = findViewById(R.id.editTextNome)
        editTextDisciplina = findViewById(R.id.editTextDisciplina)
        listView = findViewById(R.id.listView)
        buttonInsert = findViewById(R.id.buttonInsert)
        buttonUpdate = findViewById(R.id.buttonUpdate)
        buttonDelete = findViewById(R.id.buttonDelete)
        buttonListar = findViewById(R.id.buttonListar)

        // Configura ação do botão Inserir para chamar a função insertCadastro()
        buttonInsert.setOnClickListener { insertCadastro() }
        // Configura ação do botão Atualizar para chamar a função updateCadastro()
        buttonUpdate.setOnClickListener { updateCadastro() }
        // Configura ação do botão Deletar para chamar a função deleteCadastro()
        buttonDelete.setOnClickListener { deleteCadastro() }

        // Ao abrir a Activity, já carrega e mostra a lista de cadastros
        displayCadastro()

        // Configura ação do botão Listar para abrir outra Activity com a lista completa
        buttonListar.setOnClickListener {
            val intent = Intent(this, MainActivityListAluno::class.java)
            startActivity(intent)
        }
    }

    // Função que insere um novo cadastro no banco
    private fun insertCadastro() {
        // Captura o texto digitado nos campos nome e disciplina, retirando espaços extras
        val nome = editTextNome.text.toString().trim()
        val disciplina = editTextDisciplina.text.toString().trim()

        // Validação: verifica se os campos obrigatórios não estão vazios
        if (nome.isEmpty() || disciplina.isEmpty()) {
            // Exibe mensagem informando para preencher corretamente os campos
            Toast.makeText(this, "Preencha o nome e a disciplina corretamente.", Toast.LENGTH_SHORT).show()
            return  // Sai da função para evitar inserir dados inválidos
        }

        try {
            // Tenta inserir o cadastro usando o método do dbHelper
            dbHelper.insertCadastro(nome, disciplina)
            // Informa o usuário que a inserção foi bem sucedida
            Toast.makeText(this, "Cadastro inserido com sucesso!", Toast.LENGTH_SHORT).show()
            // Limpa os campos para nova inserção
            clearFields()
            // Atualiza a lista visível para refletir o novo cadastro
            displayCadastro()
        } catch (e: SQLException) {
            // Caso ocorra um erro no banco, informa ao usuário
            Toast.makeText(this, "Erro ao inserir cadastro: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // Função para atualizar um cadastro já existente
    private fun updateCadastro() {
        // Captura o ID digitado e tenta converter para inteiro (null se inválido)
        val id = editTextId.text.toString().toIntOrNull()
        val nome = editTextNome.text.toString().trim()
        val disciplina = editTextDisciplina.text.toString().trim()

        // Validação dos dados para atualização (todos campos obrigatórios)
        if (id == null || nome.isEmpty() || disciplina.isEmpty()) {
            Toast.makeText(this, "Preencha o ID, nome e disciplina corretamente.", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            // Tenta atualizar o cadastro no banco e recebe quantas linhas foram modificadas
            val rowsAffected = dbHelper.updateCadastro(id, nome, disciplina)
            if (rowsAffected > 0) {
                Toast.makeText(this, "Cadastro atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                clearFields()
                displayCadastro()
            } else {
                // Caso não tenha atualizado nenhuma linha (ID não encontrado)
                Toast.makeText(this, "Cadastro com ID $id não encontrado.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: SQLException) {
            Toast.makeText(this, "Erro ao atualizar cadastro: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // Função para deletar cadastro pelo ID
    private fun deleteCadastro() {
        val id = editTextId.text.toString().toIntOrNull()

        if (id == null) {
            Toast.makeText(this, "Preencha o ID corretamente.", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val rowsAffected = dbHelper.deleteCadastro(id)
            if (rowsAffected > 0) {
                Toast.makeText(this, "Cadastro deletado com sucesso!", Toast.LENGTH_SHORT).show()
                clearFields()
                displayCadastro()
            } else {
                Toast.makeText(this, "Cadastro com ID $id não encontrado.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: SQLException) {
            Toast.makeText(this, "Erro ao deletar o cadastro: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // Função que busca todos os cadastros no banco e exibe na ListView
    private fun displayCadastro() {
        // Obtém um cursor com todos os registros
        val cursor = dbHelper.getAllCadastro()
        val cadastros = mutableListOf<String>()

        // Percorre todos os registros retornados pelo cursor
        if (cursor.moveToFirst()) {
            do {
                // Extrai os dados das colunas pelo nome da coluna
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID))
                val nome = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME))
                val disciplina = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DISCIPLINA))
                // Formata a string para mostrar na lista
                cadastros.add("ID: $id - Nome: $nome - Disciplina: $disciplina")
            } while (cursor.moveToNext())
        }

        // Fecha o cursor para liberar recursos
        cursor.close()

        // Caso não exista nenhum cadastro, mostra uma mensagem padrão
        if (cadastros.isEmpty()) {
            cadastros.add("Nenhum cadastro encontrado.")
        }

        // Cria um ArrayAdapter para popular a ListView com as strings formatadas
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cadastros)
        // Aplica o adapter à ListView
        listView.adapter = adapter
    }

    // Função para limpar os campos do formulário, deixando prontos para novo cadastro
    private fun clearFields() {
        editTextId.text.clear()
        editTextNome.text.clear()
        editTextDisciplina.text.clear()
    }

}