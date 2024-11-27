package ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

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
        setContentView(R.layout.activity_lista_produtos);

        // Instanciação do Recycler View
        RecyclerView recyclerView = findViewById(R.id.recyclerViewProdutos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        String email = intent.getStringExtra("EMAIL");

        produtos.add(new Produto(R.drawable.lisa,"Alface Lisa", "Alface Lisa fresca, de folhas verdes e crocantes, ideal para saladas e sanduíches. Com sabor suave e textura macia, é uma opção saudável e refrescante para suas refeições diárias. Cultivada com cuidado, garantindo qualidade e frescor em cada folha. Perfeita para acompanhar seus pratos ou ser a base de uma deliciosa salada.", new BigDecimal("6.00"), 15));
        produtos.add(new Produto(R.drawable.roxa,"Alface Roxa", "Alface Roxa fresca, com folhas macias e crocantes, trazendo um sabor suave e levemente adocicado. Ideal para adicionar cor e frescor às suas saladas, sanduíches e pratos diversos. Cultivada com cuidado para garantir alta qualidade e frescor em cada folha. Perfeita para dar um toque especial às suas refeições, além de ser uma opção nutritiva e cheia de sabor.", new BigDecimal("5.00"), 15));
        produtos.add(new Produto(R.drawable.crespa,"Alface Crespa", "Alface Crespa fresca, com folhas delicadas e crocantes, oferecendo um sabor suave e refrescante. Sua textura única adiciona um toque especial a saladas, sanduíches e pratos variados. Cultivada com cuidado para garantir qualidade e frescor em cada folha. Perfeita para trazer mais leveza e sabor às suas refeições, além de ser uma opção saudável e nutritiva.", new BigDecimal("4.00"), 15));

        ProdutoAdapter adapter = new ProdutoAdapter(produtos);
        recyclerView.setAdapter(adapter);

        ((ImageButton) findViewById(R.id.btCarrinho)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaProdutosActivity.this, CarrinhoActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
            }
        });

        ((ImageButton) findViewById(R.id.btPerfil)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaProdutosActivity.this, EditarPerfilActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
            }
        });

    }
}
