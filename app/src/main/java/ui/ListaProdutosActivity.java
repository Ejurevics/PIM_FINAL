package ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pim_raizesurbanas.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import model.Produto;
import ui.Adapters.ProdutoAdapter;

public class ListaProdutosActivity extends AppCompatActivity {

    List<Produto> produtos = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos); // Define o layout da Activity.

        // Instanciação do Recycler View
        RecyclerView recyclerView = findViewById(R.id.recyclerViewProdutos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        produtos.add(new Produto(R.drawable.lisa,"Alface Lisa", "Folhas macias e suaves.", new BigDecimal("4.00"), 10));
        produtos.add(new Produto(R.drawable.roxa,"Alface Roxa", "Folhas roxas com antioxidantes.", new BigDecimal("6.00"), 10));
        produtos.add(new Produto(R.drawable.crespa,"Alface Crespa", "Folhas crocantes e sabor suave.", new BigDecimal("5.00"), 20));

        ProdutoAdapter adapter = new ProdutoAdapter(produtos);
        recyclerView.setAdapter(adapter);

        ((ImageButton) findViewById(R.id.btCarrinho)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaProdutosActivity.this, CarrinhoActivity.class);
                startActivity(intent);
            }
        });

    }
}
