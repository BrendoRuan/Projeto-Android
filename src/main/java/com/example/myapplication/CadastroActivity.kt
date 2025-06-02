// Declaração do pacote onde esta classe está localizada
package com.example.myapplication

// Importações necessárias para a funcionalidade da Activity
import android.app.DatePickerDialog // (não utilizado no código atual)
import android.content.Intent       // Usado para navegação entre Activities
import android.os.Bundle            // Usado para manipular o estado da Activity
import android.widget.Button        // Usado para criar e manipular botões
import android.widget.EditText      // Usado para campos de entrada de texto
import android.widget.Toast         // Usado para exibir mensagens rápidas (notificações) ao usuário
import androidx.appcompat.app.AppCompatActivity // Classe base para Activities com suporte à ActionBar
import java.util.* // (não utilizado no código atual, pode ser removido)

// Declaração da classe CadastroActivity que herda de AppCompatActivity
class CadastroActivity : AppCompatActivity() {

    // Função chamada quando a Activity é criada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Chama o método onCreate da superclasse
        setContentView(R.layout.activity_cadastro) // Define o layout da tela como o XML activity_cadastro

        // Abaixo, fazemos a ligação entre os elementos do XML e o Kotlin, através dos IDs

        val editName: EditText = findViewById(R.id.editName) // Campo de texto para o nome do usuário
        val editCPF: EditText = findViewById(R.id.editCPF) // Campo de texto para o CPF do usuário
        val editPhone: EditText = findViewById(R.id.editPhone) // Campo de texto para o telefone do usuário
        val editPassword: EditText = findViewById(R.id.editPassword) // Campo de texto para a senha
        val editConfirmPassword: EditText = findViewById(R.id.editConfirmPassword) // Campo para confirmação de senha
        val btnLogin: Button = findViewById(R.id.btnLogin) // Botão de ação para efetuar o cadastro

        // Define o comportamento ao clicar no botão de cadastro
        btnLogin.setOnClickListener {
            // Obtém o texto dos campos de entrada, removendo espaços em branco extras
            val name = editName.text.toString().trim()
            val cpf = editCPF.text.toString().trim()
            val phone = editPhone.text.toString().trim()
            val password = editPassword.text.toString().trim()
            val confirmPassword = editConfirmPassword.text.toString().trim()

            // Chama a função de validação dos campos. Se for válida, executa o bloco abaixo
            if (validateFields(name, cpf, phone, password, confirmPassword)) {
                // Exibe mensagem de sucesso
                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show()

                // Aqui você poderia incluir lógica para salvar os dados em um banco (Firebase, API, etc.)

                // Redireciona o usuário para a tela de login (MainActivity)
                startActivity(Intent(this, MainActivity::class.java))
                finish() // Encerra a tela de cadastro para que o usuário não possa voltar com o botão "voltar"
            }
        }
    }

    // Função que valida os dados inseridos nos campos
    private fun validateFields(
        name: String,
        cpf: String,
        phone: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        // Verifica se o campo nome está vazio
        if (name.isEmpty()) {
            showToast("Por favor, preencha o campo Nome.")
            return false
        }

        // Verifica se o campo CPF está vazio
        if (cpf.isEmpty()) {
            showToast("Por favor, preencha o campo CPF.")
            return false
        }

        // Verifica se o campo telefone está vazio
        if (phone.isEmpty()) {
            showToast("Por favor, preencha o campo Número de Celular.")
            return false
        }

        // Verifica se o campo senha está vazio
        if (password.isEmpty()) {
            showToast("Por favor, preencha o campo Senha.")
            return false
        }

        // Verifica se o campo confirmação de senha está vazio
        if (confirmPassword.isEmpty()) {
            showToast("Por favor, preencha o campo de Confirmação de Senha.")
            return false
        }

        // Verifica se a senha e a confirmação de senha coincidem
        if (password != confirmPassword) {
            showToast("As senhas não coincidem. Tente novamente.")
            return false
        }

        // Todos os campos estão válidos
        return true
    }

    // Função auxiliar para mostrar mensagens rápidas ao usuário
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}