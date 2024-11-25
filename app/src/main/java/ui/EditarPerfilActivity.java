package ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pim_raizesurbanas.R;
import model.Cliente;
import services.ClienteApiService;

public class EditarPerfilActivity extends AppCompatActivity {

    private EditText textNome, textTelefone, textEmail, textSenha;
    private Button btnEditarCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        textNome = findViewById(R.id.textNome);
        textTelefone = findViewById(R.id.textTelefone);
        textEmail = findViewById(R.id.textEmail);
        textSenha = findViewById(R.id.textSenha);
        btnEditarCadastro = findViewById(R.id.btnEditarCadastro);

        // Definindo inicialmente os campos como "não editáveis"
        textNome.setEnabled(false);
        textTelefone.setEnabled(false);
        textEmail.setEnabled(false);
        textSenha.setEnabled(false);

        // Inicialmente o botão vai ter o texto "Editar Perfil"
        btnEditarCadastro.setText("Editar Perfil");

        // Ao clicar no botão Editar Perfil ou Salvar Alterações
        btnEditarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEnabled = textNome.isEnabled();
                if (!isEnabled) {
                    salvarAlteracoes();
                }

                textNome.setEnabled(!isEnabled);
                textTelefone.setEnabled(!isEnabled);
                textEmail.setEnabled(!isEnabled);
                textSenha.setEnabled(!isEnabled);

                // Altera o texto do botão para "Salvar Alterações" se estiver editando
                if (isEnabled) {
                    btnEditarCadastro.setText("Salvar Alterações");
                } else {
                    btnEditarCadastro.setText("Editar Perfil");
                }
            }
        });
    }

    private void salvarAlteracoes() {
        String nome = textNome.getText().toString().trim();
        String telefone = textTelefone.getText().toString().trim();
        String email = textEmail.getText().toString().trim();
        String senha = textSenha.getText().toString().trim();

        // Validação de campos vazios
        if (nome.isEmpty() || telefone.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validação do Email
        if (!email.contains("@")) {
            Toast.makeText(this, "O email deve conter o caractere '@'", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validação do Telefone
        if (!telefone.matches("^\\d{2}\\d{9}$")) {
            Toast.makeText(this, "O telefone deve conter o DDD e ter 11 dígitos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validação da Senha
        if (senha.length() < 8) {
            Toast.makeText(this, "A senha deve ter pelo menos 8 dígitos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criação do objeto Cliente com os dados validados
        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNome(nome);
        clienteAtualizado.setTelefone(telefone);
        clienteAtualizado.setEmail(email);
        clienteAtualizado.setSenha(senha);

        // Passando o email em vez do id
        new AtualizarClienteTask().execute(email, clienteAtualizado);
    }


    private class AtualizarClienteTask extends AsyncTask<Object, Void, String> {

        @Override
        protected String doInBackground(Object... params) {
            String email = (String) params[0];
            Cliente clienteAtualizado = (Cliente) params[1];

            try {
                // Atualizando cliente utilizando o email
                return ClienteApiService.updateCliente(email, clienteAtualizado);
            } catch (Exception e) {
                e.printStackTrace();
                return "Erro ao atualizar os dados do cliente.";
            }
        }

        @Override
        protected void onPostExecute(String resultado) {
            Toast.makeText(EditarPerfilActivity.this, resultado, Toast.LENGTH_SHORT).show();
        }
    }
}
