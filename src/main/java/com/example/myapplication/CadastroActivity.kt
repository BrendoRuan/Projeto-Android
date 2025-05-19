// Declaração do pacote onde esta classe está localizada
package com.example.myapplication

// Importações necessárias para a funcionalidade da Activity
import android.app.DatePickerDialog // (não utilizado no código atual)
import android.content.Intent       // Para navegar entre activities
import android.os.Bundle            // Para lidar com o estado da Activity
import android.widget.Button        // Para manipular botões
import android.widget.EditText      // Para campos de entrada de texto
import android.widget.Toast         // Para exibir mensagens rápidas ao usuário
import androidx.appcompat.app.AppCompatActivity // Classe base para activities que usam a ActionBar
import java.util.*                 // (não utilizado no código atual)

// Declaração da classe CadastroActivity que herda de AppCompatActivity
class CadastroActivity : AppCompatActivity() {

    // Função que é chamada quando a activity é criada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Chama a implementação da superclasse
        setContentView(R.layout.activity_cadastro) // Define o layout da tela como activity_cadastro.xml

        // Referência aos componentes de entrada definidos no XML pelo ID
        val editName: EditText = findViewById(R.id.editName) // Campo para o nome do usuário
        val editCPF: EditText = findViewById(R.id.editCPF)   // Campo para o CPF do usuário
        val editPhone: EditText = findViewById(R.id.editPhone) // Campo para o número de celular
        val editPassword: EditText = findViewById(R.id.editPassword) // Campo para a senha
        val editConfirmPassword: EditText = findViewById(R.id.editConfirmPassword) // Campo para confirmação da senha
        val btnLogin: Button = findViewById(R.id.btnLogin) // Botão para realizar o cadastro

        // Define o comportamento ao clicar no botão de cadastro
        btnLogin.setOnClickListener {
            // Obtém o texto inserido nos campos, removendo espaços extras
            val name = editName.text.toString().trim()
            val cpf = editCPF.text.toString().trim()
            val phone = editPhone.text.toString().trim()
            val password = editPassword.text.toString().trim()
            val confirmPassword = editConfirmPassword.text.toString().trim()

            // Verifica se os campos são válidos usando a função validateFields
            if (validateFields(name, cpf, phone, password, confirmPassword)) {
                // Mostra uma mensagem de sucesso
                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show()

                // Aqui seria o local para incluir lógica de salvamento no backend (API, Firebase etc.)

                // Redireciona o usuário para a tela de login (MainActivity)
                startActivity(Intent(this, MainActivity::class.java))
                finish() // Encerra a tela de cadastro para não voltar com o botão "voltar"
            }
        }
    }

    // Função que valida os dados inseridos pelo usuário
    private fun validateFields(
        name: String,
        cpf: String,
        phone: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        // Verifica se o nome está vazio
        if (name.isEmpty()) {
            showToast("Por favor, preencha o campo Nome.")
            return false
        }
        // Verifica se o CPF está vazio
        if (cpf.isEmpty()) {
            showToast("Por favor, preencha o campo CPF.")
            return false
        }
        // Verifica se o telefone está vazio
        if (phone.isEmpty()) {
            showToast("Por favor, preencha o campo Número de Celular.")
            return false
        }
        // Verifica se a senha está vazia
        if (password.isEmpty()) {
            showToast("Por favor, preencha o campo Senha.")
            return false
        }
        // Verifica se a confirmação de senha está vazia
        if (confirmPassword.isEmpty()) {
            showToast("Por favor, preencha o campo de Confirmação de Senha.")
            return false
        }
        // Verifica se as senhas digitadas são iguais
        if (password != confirmPassword) {
            showToast("As senhas não coincidem. Tente novamente.")
            return false
        }
        // Todos os campos estão válidos
        return true
    }

    // Função auxiliar para exibir mensagens curtas na tela
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
