package ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pim_raizesurbanas.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.CarrinhoDeCompras;
import model.Endereco;

public class TelaPedidoActivity extends AppCompatActivity {

    Random random = new Random();
    private RadioGroup radioGroupPagamento;
    private Spinner spinnerEndereco;
    private Button btnFinalizarPedido;
    private TextView resumoCompra;
    private TextView textEndereco;
    private TextView textOpcoesPagamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_pedido);

        // Inicializar os componentes
        radioGroupPagamento = findViewById(R.id.radioGroupPagamento);
        spinnerEndereco = findViewById(R.id.spinnerEndereco);
        btnFinalizarPedido = findViewById(R.id.btnFinalizarPedido);
        resumoCompra = findViewById(R.id.resumoCompra);
        textEndereco = findViewById(R.id.textEndereco);
        textOpcoesPagamento = findViewById(R.id.textOpcoesPagamento);

        // Recuperar o email da intent
        Intent intent = getIntent();
        String email = intent.getStringExtra("EMAIL");

        resumoCompra.setText(CarrinhoDeCompras.getTotal());

        // Listener para o botão de finalizar pedido
        btnFinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizarPedido(email); // Passar o email ao método
            }
        });

        // Configurar outros listeners se necessário, como para o RadioGroup e Spinner
        radioGroupPagamento.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                String tipoPagamento = selectedRadioButton.getText().toString();
                Toast.makeText(TelaPedidoActivity.this, "Pagamento Selecionado: " + tipoPagamento, Toast.LENGTH_SHORT).show();
            }
        });

        spinnerEndereco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String enderecoSelecionado = parentView.getItemAtPosition(position).toString();
                Toast.makeText(TelaPedidoActivity.this, "Endereço Selecionado: " + enderecoSelecionado, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        // Configurar o Spinner com endereços fictícios
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(new Endereco());

        ArrayAdapter<Endereco> adapterEndereco = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                enderecos
        );
        adapterEndereco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEndereco.setAdapter(adapterEndereco);
    }

    private void finalizarPedido(String email) {
        // Coletar as informações do resumo de compra, pagamento e endereço
        String resumo = resumoCompra.getText().toString();
        int idPagamentoSelecionado = radioGroupPagamento.getCheckedRadioButtonId();
        RadioButton radioButtonPagamento = findViewById(idPagamentoSelecionado);
        String pagamentoSelecionado = radioButtonPagamento.getText().toString();
        String enderecoSelecionado = spinnerEndereco.getSelectedItem().toString();

        // Resumo da compra
        String mensagem = "Pedido Finalizado\n" +
                "Resumo: " + resumo + "\n" +
                "Pagamento: " + pagamentoSelecionado + "\n" +
                "Endereço: " + enderecoSelecionado;

        Intent intent = new Intent(TelaPedidoActivity.this, resumoPedidoActivity.class);
        intent.putExtra("Resumo", mensagem);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }
}
