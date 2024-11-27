package ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pim_raizesurbanas.R;
import model.CarrinhoDeCompras;

public class ResumoPedidoActivity extends AppCompatActivity {
    TextView pedidoResumo, pedidoValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_resumo_pedido);
        Intent intent = getIntent();
        String resumo = intent.getStringExtra("Resumo");
        pedidoResumo = findViewById(R.id.tvPedidoResumo);
        pedidoValor = findViewById(R.id.tvPedidoValor);

        pedidoResumo.setText(resumo);
        pedidoValor.setText(CarrinhoDeCompras.getTotal());
    }
}
