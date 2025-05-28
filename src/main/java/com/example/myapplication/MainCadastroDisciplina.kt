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

class MainCadastroDisciplina : AppCompatActivity(){

    // Declaração das variáveis que representam componentes da interface
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var editTextId: EditText
    private lateinit var editTextDisciplina: EditText
    private lateinit var listView: ListView
    private lateinit var buttonInsert: Button
    private lateinit var buttonUpdate: Button
    private lateinit var buttonDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Define qual layout XML será usado nesta Activity
        setContentView(R.layout.activity_cadastro_disciplina)

        editTextId = findViewById(R.id.editTextId)
        editTextDisciplina = findViewById(R.id.editTextDisciplina)
        listView = findViewById(R.id.listView)
        buttonInsert = findViewById(R.id.buttonInsert)
        buttonUpdate = findViewById(R.id.buttonUpdate)
        buttonDelete = findViewById(R.id.buttonDelete)

        // Define ações para os botões: inserir, atualizar e deletar registros
        buttonInsert.setOnClickListener { insertCadastro() }
        buttonUpdate.setOnClickListener { updateCadastro() }
        buttonDelete.setOnClickListener { deleteCadastro() }

        // Exibe os dados cadastrados na lista logo ao abrir a Activity
        displayCadastro()
    }

    // Função para inserir um novo cadastro no banco de dados
    private fun insertCadastro() {
        // Pega os valores digitados no campo nome e disciplina, removendo espaços extras
        val disciplina = editTextDisciplina.text.toString().trim() // Corrigido para String

        // Valida se os campos estão preenchidos
        if (disciplina.isEmpty()) {
            // Exibe mensagem ao usuário se algum campo estiver vazio
            Toast.makeText(this, "Preencha o nome e a disciplina corretamente.", Toast.LENGTH_SHORT).show()
            return // Sai da função sem executar o restante
        }

        try {
            // Chama o método do dbHelper para inserir os dados no banco
            dbHelper.insertCadastro(
                disciplina,
                disciplina = toString()
            )
            // Mostra mensagem de sucesso
            Toast.makeText(this, "Cadastro inserido com sucesso!", Toast.LENGTH_SHORT).show()
            // Limpa os campos para nova inserção
            clearFields()
            // Atualiza a lista para mostrar o novo cadastro
            displayCadastro()
        } catch (e: SQLException) {
            // Em caso de erro no banco, mostra a mensagem com o erro
            Toast.makeText(this, "Erro ao inserir cadastro: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // Função para atualizar um cadastro existente
    private fun updateCadastro() {
        // Pega o ID digitado, tenta converter para Int. Se não for número válido, retorna null
        val id = editTextId.text.toString().toIntOrNull()
        // Pega os valores digitados no campo disciplina
        val disciplina = editTextDisciplina.text.toString().trim() // Corrigido para String

        // Verifica se algum dado obrigatório está faltando
        if (id == null || disciplina.isEmpty()) {
            // Mostra aviso para o usuário preencher os campos corretamente
            Toast.makeText(this, "Preencha o ID, nome e disciplina corretamente.", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            // Tenta atualizar o cadastro no banco e recebe quantas linhas foram afetadas
            val rowsAffected = dbHelper.updateCadastro(id, disciplina, disciplina = toString())
            if (rowsAffected > 0) {
                // Se alguma linha foi atualizada, mostra sucesso
                Toast.makeText(this, "Cadastro atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                // Limpa campos após atualização
                clearFields()
                // Atualiza a lista com os dados novos
                displayCadastro()
            } else {
                // Se nenhuma linha foi alterada, provavelmente o ID não existe
                Toast.makeText(this, "Cadastro com ID $id não encontrado.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: SQLException) {
            // Em caso de erro na atualização, mostra o erro
            Toast.makeText(this, "Erro ao atualizar cadastro: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // Função para deletar um cadastro pelo ID
    private fun deleteCadastro() {
        // Pega o ID digitado e tenta converter para inteiro
        val id = editTextId.text.toString().toIntOrNull()

        if (id == null) {
            // Se ID inválido, mostra mensagem e sai da função
            Toast.makeText(this, "Preencha o ID corretamente.", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            // Tenta deletar cadastro, retorna quantas linhas foram deletadas
            val rowsAffected = dbHelper.deleteCadastro(id)
            if (rowsAffected > 0) {
                // Sucesso na deleção
                Toast.makeText(this, "Cadastro deletado com sucesso!", Toast.LENGTH_SHORT).show()
                // Limpa campos após deleção
                clearFields()
                // Atualiza lista para refletir exclusão
                displayCadastro()
            } else {
                // Caso ID não encontrado para deleção
                Toast.makeText(this, "Cadastro com ID $id não encontrado.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: SQLException) {
            // Em caso de erro na deleção, exibe mensagem
            Toast.makeText(this, "Erro ao deletar o cadastro: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // Função para exibir todos os cadastros na ListView
    private fun displayCadastro() {
        // Obtem um cursor com todos os registros do banco
        val cursor = dbHelper.getAllCadastro()
        // Lista que vai armazenar as strings para mostrar na ListView
        val cadastros = mutableListOf<String>()

        // Move para o primeiro registro e percorre todos, se existirem
        if (cursor.moveToFirst()) {
            do {
                // Extrai os dados das colunas pelo nome
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID))
                val nome = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME))
                val disciplina = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DISCIPLINA))
                // Monta a string para exibir na lista
                cadastros.add("ID: $id - Nome: $nome - Disciplina: $disciplina")
            } while (cursor.moveToNext())
        }

        // Fecha o cursor para liberar recursos
        cursor.close()

        // Se não houver nenhum cadastro, adiciona mensagem padrão
        if (cadastros.isEmpty()) {
            cadastros.add("Nenhum cadastro encontrado.")
        }

        // Cria o adapter para a ListView usando um layout padrão do Android e a lista de strings
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cadastros)
        // Aplica o adapter na ListView para mostrar os dados
        listView.adapter = adapter
    }

    // Função para limpar os campos de texto do formulário
    private fun clearFields() {
        editTextId.text.clear()
        editTextDisciplina.text.clear()
    }
}