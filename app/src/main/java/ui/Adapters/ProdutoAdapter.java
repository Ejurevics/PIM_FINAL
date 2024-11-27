package ui.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pim_raizesurbanas.R;
import java.util.List;
import model.Produto;
import ui.DetalheProdutoActivity;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {

    private List<Produto> produtos;

    public ProdutoAdapter(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_lista, parent, false);
        return new ProdutoViewHolder(itemView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Produto produto = produtos.get(position);

        // Configurando os valores
        holder.nomeProduto.setText(produto.getNome());
        holder.descricaoProduto.setText(produto.getDescricao());
        holder.imagem.setImageResource(produto.getId());
        holder.precoProduto.setText(String.format("R$ %.2f", produto.getPreco()));

        // Configurando o clique no botão
        holder.btComprar.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetalheProdutoActivity.class);
            intent.putExtra("produto", produto);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return produtos == null ? 0 : produtos.size();
    }

    // Método para atualizar os dados
    public void updateProdutos(List<Produto> novosProdutos) {
        this.produtos = novosProdutos;
        notifyDataSetChanged();
    }

    // ViewHolder
    static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        TextView nomeProduto, descricaoProduto, precoProduto;
        ImageView imagem;
        Button btComprar;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeProduto = itemView.findViewById(R.id.nomeProduto);
            descricaoProduto = itemView.findViewById(R.id.descricaoProduto);
            imagem = itemView.findViewById(R.id.imagemProduto);
            precoProduto = itemView.findViewById(R.id.precoProduto);
            btComprar = itemView.findViewById(R.id.btnComprar);
        }
    }
}
