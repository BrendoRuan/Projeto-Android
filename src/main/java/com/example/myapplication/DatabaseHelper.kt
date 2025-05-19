// Declara o pacote onde a classe está localizada
package com.example.myapplication

// Importa classes necessárias para trabalhar com banco de dados SQLite
import android.content.ContentValues // Usado para inserir/atualizar dados no banco
import android.content.Context       // Fornece contexto da aplicação
import android.database.Cursor       // Representa resultados de consultas
import android.database.sqlite.SQLiteDatabase // Classe para interagir com o banco
import android.database.sqlite.SQLiteOpenHelper // Classe base para ajudar na criação e atualização do banco

// Classe DatabaseHelper estende SQLiteOpenHelper para gerenciar banco de dados local
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Bloco companion para constantes estáticas
    companion object {
        private const val DATABASE_NAME = "MainCadastro.db" // Nome do banco de dados
        private const val DATABASE_VERSION = 1              // Versão do banco (usado em upgrades)

        // Constantes para nome da tabela e colunas
        const val TABLE_NAME = "Cadastro"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "nome"
        const val COLUMN_DISCIPLINA = "disciplina"
    }

    // Método chamado quando o banco é criado pela primeira vez
    override fun onCreate(db: SQLiteDatabase?) {
        // Comando SQL para criar a tabela com 3 colunas: id (PK), nome (texto), disciplina (texto)
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_DISCIPLINA TEXT NOT NULL
            )
        """.trimIndent()
        db?.execSQL(createTableQuery) // Executa o comando SQL
    }

    // Método chamado quando o banco precisa ser atualizado (mudança de versão)
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME") // Remove a tabela antiga, se existir
        onCreate(db) // Cria a nova estrutura do banco
    }

    // Insere um novo cadastro no banco com nome e disciplina
    fun insertCadastro(nome: String, disciplina: String): Long {
        val db = writableDatabase // Obtém o banco em modo escrita
        val values = ContentValues().apply {
            put(COLUMN_NAME, nome)              // Adiciona o nome
            put(COLUMN_DISCIPLINA, disciplina)  // Adiciona a disciplina
        }
        return db.insert(TABLE_NAME, null, values) // Insere no banco e retorna o ID da nova linha
    }

    // Atualiza um cadastro existente com base no ID
    fun updateCadastro(id: Int, nome: String, disciplina: String): Int {
        val db = writableDatabase // Obtém o banco em modo escrita
        val values = ContentValues().apply {
            put(COLUMN_NAME, nome)
            put(COLUMN_DISCIPLINA, disciplina)
        }
        // Atualiza os valores onde o ID for igual ao fornecido
        return db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(id.toString()))
    }

    // Deleta um cadastro com base no ID
    fun deleteCadastro(id: Int): Int {
        val db = writableDatabase // Obtém o banco em modo escrita
        return db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString())) // Remove a linha do ID
    }

    // Retorna todos os registros da tabela em forma de Cursor (para percorrer os dados)
    fun getAllCadastro(): Cursor {
        val db = readableDatabase // Obtém o banco em modo leitura
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null) // Retorna todos os registros
    }
}
