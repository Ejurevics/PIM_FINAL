package ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pim_raizesurbanas.R;

import model.CarrinhoDeCompras;
import model.ItemCarrinho;
import model.Produto;

public class DetalheProdutoActivity extends AppCompatActivity {

    private TextView nomeProduto, descricaoProduto, precoProduto;
    private ImageView imagemProduto;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Definindo o layout da activity
        setContentView(R.layout.activity_detalhe_produto);

        // Inicializando os elementos da interface
        nomeProduto = findViewById(R.id.nomeProdutoDetalhes);
        descricaoProduto = findViewById(R.id.descricaoProdutoDetalhes);
        imagemProduto = findViewById(R.id.imagemProdutoDetalhes);
        precoProduto = findViewById(R.id.precoProdutoDetalhes);

        // Obtendo o produto passado pela Intent
        Produto produto = (Produto) getIntent().getSerializableExtra("produto");

        // Verificando se o produto não é nulo e preenchendo os dados
        if (produto != null) {
            nomeProduto.setText(produto.getNome());
            descricaoProduto.setText(produto.getDescricao());
            imagemProduto.setImageResource(produto.getId());
            precoProduto.setText("R$ " + produto.getPreco().toString());
        }

        // Configurando o botão de adicionar ao carrinho
        Button btnAdicionarCarrinho = findViewById(R.id.btnAdicionarCarrinho);
        btnAdicionarCarrinho.setOnClickListener(view -> {
            if (produto != null) {
                ItemCarrinho itemCarrinho = new ItemCarrinho(produto, 1, produto.getPreco());
                CarrinhoDeCompras.addCarrinhoItem(itemCarrinho);
                Toast.makeText(this, "Produto adicionado ao carrinho!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}