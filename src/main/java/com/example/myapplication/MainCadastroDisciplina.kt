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

// Define uma Activity chamada MainCadastroDisciplina que herda de AppCompatActivity
class MainCadastroDisciplina : AppCompatActivity() {

    // Declaração das variáveis que vão armazenar referências aos componentes visuais e ao helper do banco
    private lateinit var dbHelper: DatabaseHelper         // Helper para manipulação do banco de dados SQLite
    private lateinit var editTextId: EditText              // Campo de texto para o ID do registro (para update/delete)
    private lateinit var editTextNome: EditText            // Campo de texto para o nome do aluno/professor/etc
    private lateinit var editTextDisciplina: EditText      // Campo de texto para o nome da disciplina
    private lateinit var listView: ListView                 // ListView para mostrar os registros cadastrados
    private lateinit var buttonInsert: Button               // Botão para inserir um novo registro
    private lateinit var buttonUpdate: Button               // Botão para atualizar um registro existente
    private lateinit var buttonDelete: Button               // Botão para deletar um registro pelo ID
    private lateinit var buttonListar: Button               // Botão para listar em outra Activity

    // Método chamado ao criar a Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Define o layout XML que será usado para esta Activity
        setContentView(R.layout.activity_cadastro_disciplina)

        // Cria uma instância do helper que vai gerenciar o banco de dados local SQLite
        dbHelper = DatabaseHelper(this)

        // Liga as variáveis de componente visual aos elementos definidos no layout XML via ID
        editTextId = findViewById(R.id.editTextId)
        editTextNome = findViewById(R.id.editTextNome)
        editTextDisciplina = findViewById(R.id.editTextDisciplina)
        listView = findViewById(R.id.listView)
        buttonInsert = findViewById(R.id.buttonInsert)
        buttonUpdate = findViewById(R.id.buttonUpdate)
        buttonDelete = findViewById(R.id.buttonDelete)
        buttonListar = findViewById(R.id.buttonListar)

        // Configura ação para o botão Insert: chama a função insertCadastro ao clicar
        buttonInsert.setOnClickListener { insertCadastro() }
        // Configura ação para o botão Update: chama a função updateCadastro ao clicar
        buttonUpdate.setOnClickListener { updateCadastro() }
        // Configura ação para o botão Delete: chama a função deleteCadastro ao clicar
        buttonDelete.setOnClickListener { deleteCadastro() }

        // Configura ação para o botão Listar: abre outra Activity para mostrar lista detalhada
        buttonListar.setOnClickListener {
            val intent = Intent(this, MainActivityListDisciplina::class.java)
            startActivity(intent)
        }

        // Exibe os dados já cadastrados na ListView assim que a Activity abrir
        displayCadastro()
    }

    // Função para inserir um novo registro no banco
    private fun insertCadastro() {
        // Pega os valores digitados nos campos nome e disciplina, removendo espaços extras no começo/fim
        val nome = editTextNome.text.toString().trim()
        val disciplina = editTextDisciplina.text.toString().trim()

        // Verifica se os campos nome e disciplina não estão vazios
        if (nome.isEmpty() || disciplina.isEmpty()) {
            // Se algum campo obrigatório estiver vazio, exibe mensagem de aviso ao usuário
            Toast.makeText(this, "Preencha o nome e a disciplina corretamente.", Toast.LENGTH_SHORT).show()
            return // Interrompe a execução da função para evitar inserir dados inválidos
        }

        try {
            // Chama o método do dbHelper que insere o registro no banco SQLite
            dbHelper.insertCadastro(nome, disciplina)
            // Exibe mensagem de sucesso para o usuário
            Toast.makeText(this, "Cadastro inserido com sucesso!", Toast.LENGTH_SHORT).show()
            // Limpa os campos para permitir nova inserção sem dados antigos
            clearFields()
            // Atualiza a ListView para refletir o novo registro inserido
            displayCadastro()
        } catch (e: SQLException) {
            // Caso ocorra algum erro de banco, exibe mensagem com o detalhe do erro
            Toast.makeText(this, "Erro ao inserir cadastro: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // Função para atualizar um cadastro existente no banco
    private fun updateCadastro() {
        // Pega o ID digitado no campo, tenta converter para inteiro (null se inválido)
        val id = editTextId.text.toString().toIntOrNull()
        // Pega o nome e disciplina digitados, removendo espaços extras
        val nome = editTextNome.text.toString().trim()
        val disciplina = editTextDisciplina.text.toString().trim()

        // Verifica se todos os campos necessários estão preenchidos corretamente
        if (id == null || nome.isEmpty() || disciplina.isEmpty()) {
            // Se algum dado estiver incorreto ou faltando, avisa o usuário
            Toast.makeText(this, "Preencha o ID, nome e disciplina corretamente.", Toast.LENGTH_SHORT).show()
            return // Interrompe a execução para evitar update inválido
        }

        try {
            // Chama o método do dbHelper que tenta atualizar o registro no banco, retornando número de linhas afetadas
            val rowsAffected = dbHelper.updateCadastro(id, nome, disciplina)
            if (rowsAffected > 0) {
                // Se alguma linha foi atualizada com sucesso, mostra mensagem positiva
                Toast.makeText(this, "Cadastro atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                // Limpa os campos para nova entrada
                clearFields()
                // Atualiza a lista na tela para mostrar dados novos
                displayCadastro()
            } else {
                // Se nenhuma linha foi afetada, provavelmente o ID informado não existe no banco
                Toast.makeText(this, "Cadastro com ID $id não encontrado.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: SQLException) {
            // Em caso de erro na operação, exibe mensagem com a descrição do erro
            Toast.makeText(this, "Erro ao atualizar cadastro: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // Função para deletar um cadastro pelo ID informado
    private fun deleteCadastro() {
        // Tenta obter o ID digitado e convertê-lo para inteiro
        val id = editTextId.text.toString().toIntOrNull()

        if (id == null) {
            // Se o ID for inválido (vazio ou texto), avisa o usuário para corrigir
            Toast.makeText(this, "Preencha o ID corretamente.", Toast.LENGTH_SHORT).show()
            return // Interrompe a execução para não tentar deletar com ID inválido
        }

        try {
            // Chama o método do dbHelper que tenta deletar o registro e retorna quantas linhas foram deletadas
            val rowsAffected = dbHelper.deleteCadastro(id)
            if (rowsAffected > 0) {
                // Se deletou com sucesso, informa o usuário
                Toast.makeText(this, "Cadastro deletado com sucesso!", Toast.LENGTH_SHORT).show()
                // Limpa os campos para nova operação
                clearFields()
                // Atualiza a lista para refletir a remoção do registro
                displayCadastro()
            } else {
                // Se nenhum registro com aquele ID foi encontrado, informa usuário
                Toast.makeText(this, "Cadastro com ID $id não encontrado.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: SQLException) {
            // Caso ocorra erro na operação de deleção, mostra a mensagem com o erro
            Toast.makeText(this, "Erro ao deletar o cadastro: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // Função para mostrar todos os registros cadastrados na ListView
    private fun displayCadastro() {
        // Pega um cursor que aponta para todos os registros da tabela
        val cursor = dbHelper.getAllCadastro()
        // Lista que armazenará as strings que serão exibidas na ListView
        val cadastros = mutableListOf<String>()

        // Verifica se há pelo menos um registro no cursor
        if (cursor.moveToFirst()) {
            do {
                // Extrai os valores das colunas pelo nome definido no DatabaseHelper
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID))
                val nome = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME))
                val disciplina = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DISCIPLINA))
                // Monta uma string formatada para mostrar na lista
                cadastros.add("ID: $id - Nome: $nome - Disciplina: $disciplina")
            } while (cursor.moveToNext()) // Move para o próximo registro até acabar
        }

        // Fecha o cursor para liberar recursos do banco
        cursor.close()

        // Se a lista estiver vazia (nenhum cadastro), adiciona mensagem padrão para mostrar ao usuário
        if (cadastros.isEmpty()) {
            cadastros.add("Nenhum cadastro encontrado.")
        }

        // Cria um adapter de ArrayAdapter para ligar a lista de strings à ListView usando layout padrão simples
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cadastros)
        // Define o adapter na ListView para mostrar os dados
        listView.adapter = adapter
    }

    // Função para limpar o conteúdo dos campos de entrada de texto (formulário)
    private fun clearFields() {
        editTextId.text.clear()          // Limpa o campo ID
        editTextNome.text.clear()        // Limpa o campo nome
        editTextDisciplina.text.clear()  // Limpa o campo disciplina
    }
}
