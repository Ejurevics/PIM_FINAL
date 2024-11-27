package ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pim_raizesurbanas.R;

import org.json.JSONObject;

import model.CarrinhoDeCompras;
import model.Cliente;
import services.ClienteApiService;
import ui.ResumoPedidoActivity;

public class TelaPedidoActivity extends AppCompatActivity {

    private RadioGroup radioGroupPagamento;
    private Button btnFinalizarPedido;
    private TextView resumoCompra;
    private TextView textEndereco;
    private TextView enderecoCliente;
    private TextView textOpcoesPagamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_pedido);

        // Inicializar os componentes
        radioGroupPagamento = findViewById(R.id.radioGroupPagamento);
        btnFinalizarPedido = findViewById(R.id.btnFinalizarPedido);
        resumoCompra = findViewById(R.id.resumoCompra);
        textEndereco = findViewById(R.id.textEndereco);
        enderecoCliente = findViewById(R.id.enderecoCliente);
        textOpcoesPagamento = findViewById(R.id.textOpcoesPagamento);

        // Recuperar o email da intent
        Intent intent = getIntent();
        String email = intent.getStringExtra("EMAIL");
        Toast.makeText(TelaPedidoActivity.this, "E-mail recebido: " + email, Toast.LENGTH_SHORT).show();

        // Obter o endereço do cliente
        if (email != null) {
            new BuscarEnderecoTask().execute(email);
        }

        resumoCompra.setText(CarrinhoDeCompras.getTotal());

        // Finalizar pedido
        btnFinalizarPedido.setOnClickListener(v -> finalizarPedido(email));
    }

    private void finalizarPedido(String email) {
        // Verificar se o e-mail é válido
        if (email == null || email.isEmpty()) {
            Toast.makeText(TelaPedidoActivity.this, "E-mail inválido.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Coletar as informações do resumo de compra, pagamento e endereço
        String resumo = resumoCompra.getText().toString();
        int idPagamentoSelecionado = radioGroupPagamento.getCheckedRadioButtonId();
        RadioButton radioButtonPagamento = findViewById(idPagamentoSelecionado);
        String pagamentoSelecionado = radioButtonPagamento != null ? radioButtonPagamento.getText().toString() : "Não selecionado";
        String enderecoSelecionado = enderecoCliente.getText().toString();

        // Resumo da compra
        String mensagem = String.format("Pedido Finalizado\nResumo: %s\nPagamento: %s\nEndereço: %s", resumo, pagamentoSelecionado, enderecoSelecionado);

        // Verificar se algum dado essencial está faltando
        if (resumo.isEmpty() || pagamentoSelecionado.isEmpty() || enderecoSelecionado.equals("Endereço de Cliente Aqui")) {
            Toast.makeText(TelaPedidoActivity.this, "Preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Iniciar a próxima Activity
        Intent intent = new Intent(TelaPedidoActivity.this, ResumoPedidoActivity.class);
        intent.putExtra("Resumo", mensagem);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }

    private class BuscarEnderecoTask extends AsyncTask<String, Void, Cliente> {

        @Override
        protected Cliente doInBackground(String... params) {
            String email = params[0];
            Cliente cliente = null;
            try {
                // Chamar o método da API para buscar o endereço
                String enderecoResponse = ClienteApiService.buscarEnderecoCliente(email);

                // Verifique o conteúdo da resposta da API
                Log.d("EnderecoJSON", "Resposta da API: " + enderecoResponse);

                // Verificar se a resposta não é nula ou vazia
                if (enderecoResponse != null && !enderecoResponse.isEmpty()) {
                    // Tentar processar a resposta separando pela vírgula
                    String[] enderecoParts = enderecoResponse.split(",");
                    if (enderecoParts.length == 2) {
                        String rua = enderecoParts[0].trim();
                        String numeroStr = enderecoParts[1].trim();
                        int numero = Integer.parseInt(numeroStr); // Converter o número para inteiro

                        // Criar o cliente e definir o endereço
                        cliente = new Cliente();
                        cliente.setRua(rua);
                        cliente.setNumero(numero);

                        // Log para depuração
                        Log.d("EnderecoJSON", "Rua: " + rua);
                        Log.d("EnderecoJSON", "Número: " + numero);
                    } else {
                        Log.e("EnderecoJSON", "Resposta da API não contém rua e número corretamente.");
                    }
                } else {
                    Log.e("EnderecoJSON", "Resposta da API está vazia ou nula.");
                }
            } catch (Exception e) {
                Log.e("EnderecoJSON", "Erro ao processar a resposta da API: " + e.getMessage());
                e.printStackTrace();
            }
            return cliente;
        }

        @Override
        protected void onPostExecute(Cliente cliente) {
            super.onPostExecute(cliente);

            // Verificar se o cliente foi corretamente instanciado
            if (cliente != null) {
                String enderecoCompleto = cliente.getRua() + ", " + cliente.getNumero();
                Log.d("Endereco", "Endereço completo: " + enderecoCompleto);
                enderecoCliente.setText(enderecoCompleto);
            } else {
                Log.d("Endereco", "Cliente ou endereço não encontrado.");
                enderecoCliente.setText("Endereço não encontrado!");
            }
        }
    }




}
