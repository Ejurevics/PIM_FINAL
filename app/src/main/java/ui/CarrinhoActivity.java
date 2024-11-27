package ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pim_raizesurbanas.R;

import model.CarrinhoDeCompras;
import ui.Adapters.CarrinhoDeComprasAdapter;

public class CarrinhoActivity extends AppCompatActivity implements CarrinhoDeComprasAdapter.TotalUpdateListener {

    private TextView resumoCompra;
    private CarrinhoDeComprasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        resumoCompra = findViewById(R.id.resumoCompra);

        // Inicialize o RecyclerView e o Adapter
        RecyclerView recyclerView = findViewById(R.id.rvCarrinho);
        adapter = new CarrinhoDeComprasAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Defina o listener para atualizar o total
        adapter.setTotalUpdateListener(this);

        Intent intent = getIntent();
        String email = intent.getStringExtra("EMAIL");

        // Caso já haja algum total inicial
        resumoCompra.setText(CarrinhoDeCompras.getTotal());
        ((Button) findViewById(R.id.btnFinalizarCompra)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CarrinhoDeCompras.produtos.isEmpty()) {
                    Toast.makeText(CarrinhoActivity.this, "Não é possível prosseguir com o carrinho vazio", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(CarrinhoActivity.this, TelaPedidoActivity.class);
                    intent.putExtra("EMAIL", email); // Passa o email para a próxima Activity
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onTotalUpdated(String total) {
        resumoCompra.setText("Total: " + total);
    }
}
