package ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.pim_raizesurbanas.R;
import model.Cliente;
import services.ClienteApiService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Declara e inicializa os campos de entrada e o botão.
        EditText txtEmail = findViewById(R.id.txtEmail);
        EditText txtSenha = findViewById(R.id.txtSenha);
        Button buttonSubmit = findViewById(R.id.btnEntrar);
        Button buttonSubmit2 = findViewById(R.id.btnCadastrar);

        // Define ação para o botão de entrar.
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = txtEmail.getText().toString();
                final String senha = txtSenha.getText().toString();

                // Verifica se o email e senha não estão vazios.
                if (email.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Email e senha não podem estar vazios", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Cria um objeto Cliente com as credenciais.
                Cliente cliente = new Cliente();
                cliente.setEmail(email);
                cliente.setSenha(senha);

                // Consome a API para verificar se o cliente existe.
                new Thread(() -> {
                    try {
                        String resultadoLogin = ClienteApiService.loginCliente(cliente);
                        runOnUiThread(() -> {
                            if (resultadoLogin.equals("Login realizado com sucesso!")) {
                                // Se o login foi bem-sucedido, inicia a ListaProdutosActivity.
                                Intent intent = new Intent(MainActivity.this, ListaProdutosActivity.class);
                                intent.putExtra("CLIENTE", cliente);
                                Toast.makeText(MainActivity.this, "Login confirmado com sucesso", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            } else {
                                // Se o login falhar, exibe a mensagem de erro recebida.
                                Toast.makeText(MainActivity.this, resultadoLogin, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "Erro ao acessar o servidor", Toast.LENGTH_SHORT).show());
                    }
                }).start();
            }
        });

        Button cadastrarButton = findViewById(R.id.btnCadastrar);
        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroClienteActivity.class);
                startActivity(intent);
            }
        });
    }
}
