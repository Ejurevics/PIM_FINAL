package ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pim_raizesurbanas.R;

import model.Cliente;
import services.ClienteApiService;

public class DetalheClienteActivity extends AppCompatActivity {

    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalhe_cliente);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cliente = (Cliente) getIntent().getSerializableExtra("CLIENTE");

        // Localiza os componentes de interface e define seus valores.
        TextView textNome = findViewById(R.id.textNome);
        TextView textTelefone = findViewById(R.id.textTelefone);
        TextView textEmail = findViewById(R.id.textEmail);
        TextView textSenha = findViewById(R.id.textSenha);
        TextView textRua = findViewById(R.id.textRua);
        TextView textNumero = findViewById(R.id.textNumero);

        // Se o objeto Cliente não for nulo, exibe os valores nos campos.
        if (cliente != null) {
            textNome.setText(cliente.getNome());
            textTelefone.setText(cliente.getTelefone());
            textEmail.setText(cliente.getEmail());
            textSenha.setText(cliente.getSenha());
            textRua.setText(cliente.getRua());
            textNumero.setText(String.valueOf(cliente.getNumero()));
        }

        // Botão Confirmar, que exibe uma mensagem ao ser pressionado.
        Button btnConfirmarCadastro = findViewById(R.id.btnConfirmarCadastro);
        btnConfirmarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CadastroCliente", "Nome: " + cliente.getNome() + ", Telefone: " + cliente.getTelefone() +
                        ", Email: " + cliente.getEmail() + ", Rua: " + cliente.getRua() + ", Número: " + cliente.getNumero());
                new AddClienteTask().execute(cliente);
            }
        });
    }

    // AsyncTask para adicionar cliente em segundo plano
    private class AddClienteTask extends AsyncTask<Cliente, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Cliente... clientes) {
            try {
                ClienteApiService.addCliente(clientes[0]);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(DetalheClienteActivity.this, "Cadastro confirmado com sucesso", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DetalheClienteActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(DetalheClienteActivity.this, "Falha ao confirmar cadastro", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
