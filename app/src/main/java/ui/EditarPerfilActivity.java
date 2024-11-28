package ui;

import android.content.Intent;
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
    private Button btnSalvarCadastro, btnExcluirCadastro;
    private String emailOriginal; // Armazena o e-mail original para referência interna

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        // Inicialização dos elementos da interface
        textNome = findViewById(R.id.textNome);
        textTelefone = findViewById(R.id.textTelefone);
        textEmail = findViewById(R.id.textEmail);
        textSenha = findViewById(R.id.textSenha);
        btnSalvarCadastro = findViewById(R.id.btnEditarCadastro);
        btnExcluirCadastro = findViewById(R.id.btnExcluirCadastro);

        // Receber o e-mail passado pela Intent (se necessário para referência interna)
        Intent intent = getIntent();
        emailOriginal = intent.getStringExtra("EMAIL");

        // Deixar todos os campos editáveis e vazios
        textEmail.setText("");
        textNome.setText("");
        textTelefone.setText("");
        textSenha.setText("");

        // Listener para salvar alterações
        btnSalvarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarAlteracoes();
            }
        });

        // Listener para excluir cadastro
        btnExcluirCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirCadastro();
            }
        });
    }

    private void salvarAlteracoes() {
        String nome = textNome.getText().toString().trim();
        String telefone = textTelefone.getText().toString().trim();
        String emailNovo = textEmail.getText().toString().trim();
        String senha = textSenha.getText().toString().trim();

        // Validação de campos vazios
        if (nome.isEmpty() || telefone.isEmpty() || emailNovo.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validação do Email
        if (!emailNovo.contains("@")) {
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

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNome(nome);
        clienteAtualizado.setTelefone(telefone);
        clienteAtualizado.setEmail(emailNovo);
        clienteAtualizado.setSenha(senha);

        new AtualizarClienteTask().execute(emailOriginal, clienteAtualizado);
    }


    private void excluirCadastro() {
        // Exclusão permanece inalterada
        if (emailOriginal == null || emailOriginal.isEmpty()) {
            Toast.makeText(this, "O e-mail original é necessário para excluir o cadastro.", Toast.LENGTH_SHORT).show();
            return;
        }

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Excluir Cadastro")
                .setMessage("Tem certeza de que deseja excluir este cadastro?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    new ExcluirClienteTask().execute(emailOriginal);
                })
                .setNegativeButton("Não", null)
                .show();
    }

    private class AtualizarClienteTask extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... params) {
            String emailOriginal = (String) params[0];
            Cliente clienteAtualizado = (Cliente) params[1];

            try {
                return ClienteApiService.updateCliente(emailOriginal, clienteAtualizado);
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

    private class ExcluirClienteTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String email = params[0];
            try {
                return ClienteApiService.deleteCliente(email);
            } catch (Exception e) {
                e.printStackTrace();
                return "Erro ao excluir o cliente.";
            }
        }

        @Override
        protected void onPostExecute(String resultado) {
            Toast.makeText(EditarPerfilActivity.this, resultado, Toast.LENGTH_SHORT).show();
            if (resultado.equalsIgnoreCase("Cliente excluído com sucesso")) {
                finish();
            }
        }
    }
}
