package ui;

import android.content.Intent;
import android.os.AsyncTask; // Importa AsyncTask para executar tarefas em segundo plano.
import android.os.Bundle; // Importa a classe Bundle para salvar e restaurar o estado da Activity.
import android.util.Log;
import android.view.View; // Importa a classe View para interações da interface.
import android.widget.Button; // Importa a classe Button para criar botões.
import android.widget.TextView; // Importa a classe TextView para exibir texto.
import android.widget.Toast; // Importa a classe Toast para exibir mensagens rápidas na tela.

import androidx.activity.EdgeToEdge; // Importa EdgeToEdge para otimizar o uso da tela cheia.
import androidx.appcompat.app.AppCompatActivity; // Importa AppCompatActivity para compatibilidade entre versões Android.
import androidx.core.graphics.Insets; // Importa a classe Insets para manipular margens do sistema.
import androidx.core.view.ViewCompat; // Importa ViewCompat para lidar com configurações de View.
import androidx.core.view.WindowInsetsCompat; // Importa WindowInsetsCompat para configurar margens do sistema.

import com.example.pim_raizesurbanas.R;

import model.Cliente;
import services.ClienteApiService;

public class DetalheClienteActivity extends AppCompatActivity { // Declara DetalheClienteActivity, que herda de AppCompatActivity.

    private Cliente cliente; // Declaração de uma variável para armazenar o objeto Cliente.

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Método que é executado na criação da Activity.
        super.onCreate(savedInstanceState); // Chama o método onCreate da superclasse.
        EdgeToEdge.enable(this); // Ativa o modo tela cheia.
        setContentView(R.layout.activity_detalhe_cliente); // Define o layout da Activity com activity_detalhe_cliente.xml.

        // RECEITA DE BOLO: Configura margens para lidar com system bars.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cliente = (Cliente) getIntent().getSerializableExtra("CLIENTE"); // Recebe o objeto Cliente enviado pela Intent.

        // Localiza os componentes de interface e define seus valores.
        TextView textNome = findViewById(R.id.textNome); // Campo que exibe o nome.
        TextView textTelefone = findViewById(R.id.textTelefone); // Campo que exibe o telefone.
        TextView textEmail = findViewById(R.id.textEmail); // Campo que exibe o e-mail.
        TextView textSenha = findViewById(R.id.textSenha); // Campo que exibe a senha.

        // Se o objeto Cliente não for nulo, exibe os valores nos campos.
        if (cliente != null) {
            textNome.setText(cliente.getNome());
            textTelefone.setText(cliente.getTelefone());
            textEmail.setText(cliente.getEmail());
            textSenha.setText(cliente.getSenha());
        }

        // Botão Confirmar, que exibe uma mensagem ao ser pressionado.
        Button btnConfirmarCadastro = findViewById(R.id.btnConfirmarCadastro);
        btnConfirmarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chama a AsyncTask para adicionar o cliente em segundo plano.
                Log.d("CadastroCliente", "Nome: " + textNome + ", Telefone: " + textTelefone + ", Email: " + textEmail + ", Senha: " + textSenha);
                new AddClienteTask().execute(cliente);
            }
        });

        /*// Botão Retornar, que fecha a Activity ao ser pressionado.
        Button btnRetornarEditar = findViewById(R.id.btnRetornarEditar);
        btnRetornarEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Fecha a Activity, retornando à tela anterior.
            }
        });*/
    }

    // AsyncTask para adicionar cliente em segundo plano
    private class AddClienteTask extends AsyncTask<Cliente, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Cliente... clientes) {
            try {
                ClienteApiService.addCliente(clientes[0]); // Chama o método addCliente no serviço.
                return true; // Retorna verdadeiro se a operação for bem-sucedida.
            } catch (Exception e) {
                e.printStackTrace();
                return false; // Retorna falso em caso de erro.
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(DetalheClienteActivity.this, "Cadastro confirmado com sucesso", Toast.LENGTH_SHORT).show(); // Exibe mensagem de sucesso.
                Intent intent = new Intent(DetalheClienteActivity.this, MainActivity.class);
                startActivity(intent); // Inicia a DetalheClienteActivity.
            } else {
                Toast.makeText(DetalheClienteActivity.this, "Falha ao confirmar cadastro", Toast.LENGTH_SHORT).show(); // Exibe mensagem de erro.
            }
        }
    }
}
