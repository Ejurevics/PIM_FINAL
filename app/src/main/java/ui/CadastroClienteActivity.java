package ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pim_raizesurbanas.R;

import model.Cliente;

public class CadastroClienteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        // Declara e inicializa os campos de entrada e o botão.
        EditText txtNome = findViewById(R.id.txtNome);
        EditText txtTelefone = findViewById(R.id.txtTelefone);
        EditText txtEmail = findViewById(R.id.txtEmail);
        EditText txtSenha = findViewById(R.id.txtSenha);
        EditText txtRua = findViewById(R.id.txtRua); // Novo campo para Rua
        EditText txtNumero = findViewById(R.id.txtNumero); // Novo campo para Número
        Button buttonSubmit = findViewById(R.id.buttonSubmit);

        // Define ação para o botão de enviar os dados.
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Captura os valores digitados nos campos.
                String nome = txtNome.getText().toString().trim();
                String telefone = txtTelefone.getText().toString().trim();
                String email = txtEmail.getText().toString().trim();
                String senha = txtSenha.getText().toString().trim();
                String rua = txtRua.getText().toString().trim(); // Captura o valor da Rua
                String numero = txtNumero.getText().toString().trim(); // Captura o valor do Número

                // Validação de campos vazios
                if (nome.isEmpty() || telefone.isEmpty() || email.isEmpty() || senha.isEmpty() || rua.isEmpty() || numero.isEmpty()) {
                    Toast.makeText(CadastroClienteActivity.this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validação do email
                if (!email.contains("@")) {
                    Toast.makeText(CadastroClienteActivity.this, "O email deve conter o caractere '@'", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validação do telefone
                if (!telefone.matches("^\\d{2}\\d{9}$")) {
                    Toast.makeText(CadastroClienteActivity.this, "O telefone deve conter o DDD e ter 11 dígitos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validação da senha
                if (senha.length() < 8) {
                    Toast.makeText(CadastroClienteActivity.this, "A senha deve ter pelo menos 8 dígitos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validação do número
                if (!numero.matches("^\\d+$")) {
                    Toast.makeText(CadastroClienteActivity.this, "O número deve conter apenas dígitos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Cria um objeto Cliente com os dados validados
                Cliente cliente = new Cliente(nome, email, telefone, senha, rua, Integer.parseInt(numero));

                // Cria uma Intent para enviar o objeto Cliente para a DetalheClienteActivity
                Intent intent = new Intent(CadastroClienteActivity.this, DetalheClienteActivity.class);
                intent.putExtra("CLIENTE", cliente); // Adiciona o objeto Cliente à Intent
                startActivity(intent); // Inicia a DetalheClienteActivity
            }
        });
    }
}
