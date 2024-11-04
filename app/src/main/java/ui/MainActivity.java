package ui;

import android.content.Intent; // Importa a classe Intent para a navegação entre Activities.
import android.os.Bundle; // Importa a classe Bundle para salvar e restaurar o estado da Activity.
import android.view.View; // Importa a classe View, usada para capturar interações de interface.
import android.widget.Button; // Importa a classe Button para criar botões na interface.
import android.widget.EditText; // Importa a classe EditText para campos de entrada de texto.
import android.widget.Toast;

import androidx.activity.EdgeToEdge; // Importa EdgeToEdge para otimizar o uso da tela cheia.
import androidx.appcompat.app.AppCompatActivity; // Importa AppCompatActivity, que fornece compatibilidade entre versões do Android.
import androidx.core.graphics.Insets; // Importa a classe Insets para gerenciar margens do sistema.
import androidx.core.view.ViewCompat; // Importa ViewCompat para lidar com configurações de View.
import androidx.core.view.WindowInsetsCompat; // Importa WindowInsetsCompat para gerenciar margens do sistema em views.

import com.example.pim_raizesurbanas.R;

import model.Cliente;
import services.ClienteApiService;

public class MainActivity extends AppCompatActivity { // Declara MainActivity, que herda de AppCompatActivity.

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Método que é executado na criação da Activity.
        super.onCreate(savedInstanceState); // Chama o método onCreate da superclasse.
        EdgeToEdge.enable(this); // Ativa o modo tela cheia para a Activity.
        setContentView(R.layout.activity_main); // Define o layout da Activity usando o arquivo activity_main.xml.

        // Configura margens com ViewCompat para lidar com margens do sistema.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()); // Captura as margens.
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom); // Aplica as margens ao layout.
            return insets;
        });


        // Declara e inicializa os campos de entrada e o botão.
        EditText txtEmail = findViewById(R.id.txtEmail); // Campo para o e-mail.
        EditText txtSenha = findViewById(R.id.txtSenha); // Campo para a senha.
        Button buttonSubmit = findViewById(R.id.buttonSubmit); // Botão para enviar os dados.
        Button buttonSubmit2 = findViewById(R.id.buttonSubmit2); // Botão para iniciar Activity Cadastrar Cliente

        // Define ação para o botão de entrar.
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = txtEmail.getText().toString();
                final String senha = txtSenha.getText().toString();

                // Verifica se o email e senha não estão vazios.
                if (email.isEmpty() || senha.isEmpty()) {
                    // Exibe uma mensagem de erro (pode usar um Toast ou um AlertDialog).
                    Toast.makeText(MainActivity.this, "Email e senha não podem estar vazios", Toast.LENGTH_SHORT).show();
                    return; // Retorna se os campos estiverem vazios.
                }

                // Cria um objeto Cliente com as credenciais.
                Cliente cliente = new Cliente();
                cliente.setEmail(email);
                cliente.setSenha(senha);

                // Consome a API para verificar se o cliente existe.
                new Thread(() -> {
                    try {
                        // Chama o método para realizar o login e armazena a resposta.
                        String resultadoLogin = ClienteApiService.loginCliente(cliente);

                        runOnUiThread(() -> {
                            if (resultadoLogin.equals("Login realizado com sucesso!")) {
                                // Se o login foi bem-sucedido, inicia a ListaProdutosActivity.
                                Intent intent = new Intent(MainActivity.this, ListaProdutosActivity.class);
                                intent.putExtra("CLIENTE", cliente); // Adiciona o objeto Cliente à Intent.
                                Toast.makeText(MainActivity.this, "Login confirmado com sucesso", Toast.LENGTH_SHORT).show();
                                startActivity(intent); // Inicia a ListaProdutosActivity.
                            } else {
                                // Se o login falhar, exibe a mensagem de erro recebida.
                                Toast.makeText(MainActivity.this, resultadoLogin, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Trate a exceção, como por exemplo, mostrar uma mensagem de erro ao usuário.
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "Erro ao acessar o servidor", Toast.LENGTH_SHORT).show());
                    }
                }).start(); // Inicia a thread.
            }
        });


        Button cadastrarButton = findViewById(R.id.buttonSubmit2);
        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroClienteActivity.class);
                startActivity(intent);
            }
        });
    }
}
