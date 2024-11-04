package ui;

import android.content.Intent; // Importa a classe Intent para a navegação entre Activities.
import android.os.Bundle; // Importa a classe Bundle para salvar e restaurar o estado da Activity.
import android.view.View; // Importa a classe View, usada para capturar interações de interface.
import android.widget.Button; // Importa a classe Button para criar botões na interface.
import android.widget.EditText; // Importa a classe EditText para campos de entrada de texto.

import androidx.activity.EdgeToEdge; // Importa EdgeToEdge para otimizar o uso da tela cheia.
import androidx.appcompat.app.AppCompatActivity; // Importa AppCompatActivity, que fornece compatibilidade entre versões do Android.
import androidx.core.graphics.Insets; // Importa a classe Insets para gerenciar margens do sistema.
import androidx.core.view.ViewCompat; // Importa ViewCompat para lidar com configurações de View.
import androidx.core.view.WindowInsetsCompat; // Importa WindowInsetsCompat para gerenciar margens do sistema em views.

import com.example.pim_raizesurbanas.R;

import model.Cliente;

public class CadastroClienteActivity extends AppCompatActivity { // Declara MainActivity, que herda de AppCompatActivity.

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Método que é executado na criação da Activity.
        super.onCreate(savedInstanceState); // Chama o método onCreate da superclasse.
        EdgeToEdge.enable(this); // Ativa o modo tela cheia para a Activity.
        setContentView(R.layout.activity_cadastro_cliente); // Define o layout da Activity usando o arquivo activity_main.xml.

        // Configura margens com ViewCompat para lidar com margens do sistema.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()); // Captura as margens.
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom); // Aplica as margens ao layout.
            return insets;
        });


        // Declara e inicializa os campos de entrada e o botão.
        EditText txtNome = findViewById(R.id.txtNome); // Campo para o nome.
        EditText txtTelefone = findViewById(R.id.txtTelefone); // Campo para o telefone.
        EditText txtEmail = findViewById(R.id.txtEmail); // Campo para o e-mail.
        EditText txtSenha = findViewById(R.id.txtSenha); // Campo para a senha.
        Button buttonSubmit = findViewById(R.id.buttonSubmit); // Botão para enviar os dados.

        // Define ação para o botão de enviar os dados.
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Captura os valores digitados nos campos.
                String nome = txtNome.getText().toString();
                String telefone = txtTelefone.getText().toString();
                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();

                // Cria um objeto Cliente com os dados inseridos.
                Cliente cliente = new Cliente(nome, email, telefone, senha); // O id é gerado automaticamente.

                // Cria uma Intent para enviar o objeto Cliente para a DetalheClienteActivity.
                Intent intent = new Intent(CadastroClienteActivity.this, DetalheClienteActivity.class);
                intent.putExtra("CLIENTE", cliente); // Adiciona o objeto Cliente à Intent.
                startActivity(intent); // Inicia a DetalheClienteActivity.
            }
        });
    }
}
