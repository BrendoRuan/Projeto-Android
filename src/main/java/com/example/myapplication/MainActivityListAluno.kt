// Pacote onde a classe está localizada
package com.example.myapplication

// Importações de classes do Android e da biblioteca padrão
import android.content.Intent          // Usado para navegar entre Activities
import android.os.Bundle              // Contém dados passados entre Activities
import android.widget.ArrayAdapter    // Adapta a lista de Strings para a ListView
import android.widget.Button          // Componente de botão
import android.widget.ListView        // Componente de lista (para mostrar os cadastros)
import android.widget.Toast           // Mostra pequenas mensagens na tela
import androidx.appcompat.app.AppCompatActivity // Classe base para Activities com suporte a ActionBar

// Classe principal que herda de AppCompatActivity
class MainActivityListAluno : AppCompatActivity() {

    // Declaração de variáveis para os componentes da UI e banco
    private lateinit var dbHelper: DatabaseHelper  // Ajudante para operações no banco de dados
    private lateinit var listAluno: ListView       // ListView que exibirá os alunos cadastrados
    private lateinit var btnVoltar: Button         // Botão para voltar à tela anterior

    // Método chamado ao iniciar a Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)                         // Chama o onCreate da classe pai
        setContentView(R.layout.activity_list_aluno)              // Define o layout XML que será usado

        // Inicializa o banco de dados SQLite usando a classe DatabaseHelper
        dbHelper = DatabaseHelper(this)

        // Associa os elementos da interface às variáveis usando seus IDs
        listAluno = findViewById(R.id.listAluno)
        btnVoltar = findViewById(R.id.btnVoltar)

        // Define o que acontece ao clicar no botão "Voltar"
        btnVoltar.setOnClickListener {
            // Cria um Intent para navegar de volta à tela MainHome
            val intent = Intent(this, MainHome::class.java)
            startActivity(intent)   // Inicia a nova Activity
        }

        // Exibe a lista de alunos na ListView
        displayListaAlunos()
    }

    // Função que busca os dados do banco e preenche a ListView
    private fun displayListaAlunos() {
        // Consulta todos os registros usando o método da classe DatabaseHelper
        val cursor = dbHelper.getAllCadastro()

        // Lista de Strings onde cada item representa um cadastro formatado
        val cadastros = mutableListOf<String>()

        // moveToFirst(): verifica se o Cursor não está vazio e posiciona no primeiro registro
        if (cursor.moveToFirst()) {
            do {
                // Extrai os dados das colunas de cada linha atual do Cursor
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID))
                val nome = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME))
                val disciplina = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DISCIPLINA))

                // Formata os dados para exibição e adiciona à lista
                cadastros.add("ID: $id - Nome: $nome - Disciplina: $disciplina")

                // moveToNext(): move para o próximo item no Cursor, enquanto houver registros
            } while (cursor.moveToNext())
        }

        // Fecha o cursor para liberar recursos do sistema
        cursor.close()

        // Se a lista estiver vazia, exibe uma mensagem padrão
        if (cadastros.isEmpty()) {
            cadastros.add("Nenhum cadastro encontrado.")
        }

        // Cria um ArrayAdapter para preencher a ListView com os dados da lista 'cadastros'
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cadastros)

        // Associa o adaptador à ListView
        listAluno.adapter = adapter

        // Define um listener para clique em itens da lista
        listAluno.setOnItemClickListener { _, _, position, _ ->
            // Exibe um Toast com o item selecionado
            Toast.makeText(this, cadastros[position], Toast.LENGTH_SHORT).show()
        }
    }
}
