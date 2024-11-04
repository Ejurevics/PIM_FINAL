package ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pim_raizesurbanas.R;
import java.util.ArrayList;
import java.util.List;

public class ListaProdutosActivity extends AppCompatActivity {

    private ListView listaProdutos; // Lista para exibir os produtos.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos); // Define o layout da Activity.

        listaProdutos = findViewById(R.id.listaProdutos); // Referência para o ListView.

        // Aqui você pode simular uma lista de produtos.
        List<String> produtos = new ArrayList<>();
        produtos.add("Produto 1");
        produtos.add("Produto 2");
        produtos.add("Produto 3");
        // Adicione mais produtos conforme necessário.

        // Cria um adaptador para exibir os produtos no ListView.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, produtos);
        listaProdutos.setAdapter(adapter); // Define o adaptador para o ListView.
    }
}
