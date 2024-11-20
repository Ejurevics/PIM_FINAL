package ui.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pim_raizesurbanas.R;

import model.CarrinhoDeCompras;
import model.ItemCarrinho;
public class CarrinhoDeComprasAdapter extends RecyclerView.Adapter<CarrinhoDeComprasAdapter.ProdutoViewHolder> {

    // Defina um campo para o callback
    private TotalUpdateListener totalUpdateListener;

    // Defina o callback para ser chamado quando o total mudar
    public interface TotalUpdateListener {
        void onTotalUpdated(String total);
    }

    public void setTotalUpdateListener(TotalUpdateListener listener) {
        this.totalUpdateListener = listener;
    }

    public class ProdutoViewHolder extends RecyclerView.ViewHolder {
        TextView nomeProduto, descricaoProduto, precoProduto, quantidadeProduto;
        Button btAdicionar, btRemover;
        ImageView imagem;

        public ProdutoViewHolder(View itemView) {
            super(itemView);
            nomeProduto = itemView.findViewById(R.id.nomeProduto);
            descricaoProduto = itemView.findViewById(R.id.descricaoProduto);
            precoProduto = itemView.findViewById(R.id.precoProduto);
            quantidadeProduto = itemView.findViewById(R.id.quantidadeProduto);
            imagem = itemView.findViewById(R.id.imagemProduto);
            btAdicionar = itemView.findViewById(R.id.btAdicionarQuantidade);
            btRemover = itemView.findViewById(R.id.btRemoverQuantidade);

            btAdicionar.setOnClickListener(v -> {
                if (CarrinhoDeCompras.produtos != null) {
                    ItemCarrinho produto = CarrinhoDeCompras.produtos.get(getAdapterPosition());
                    produto.setQuantidade(produto.getQuantidade() + 1);
                    quantidadeProduto.setText(String.valueOf(produto.getQuantidade()));
                    notifyItemChanged(getAdapterPosition());

                    // Recalcule o total e notifique a Activity/Fragmento
                    if (totalUpdateListener != null) {
                        String total = CarrinhoDeCompras.getTotal();
                        totalUpdateListener.onTotalUpdated(total);
                    }
                }
            });

            btRemover.setOnClickListener(v -> {
                if (CarrinhoDeCompras.produtos != null) {
                    ItemCarrinho produto = CarrinhoDeCompras.produtos.get(getAdapterPosition());
                    if (produto.getQuantidade() > 0) {
                        produto.setQuantidade(produto.getQuantidade() - 1);
                        quantidadeProduto.setText(String.valueOf(produto.getQuantidade()));
                        notifyItemChanged(getAdapterPosition());

                        // Se a quantidade for 0, remove o item do carrinho
                        if (produto.getQuantidade() == 0) {
                            CarrinhoDeCompras.delCarrinhoItem(produto);
                        }

                        // Recalcule o total e notifique a Activity/Fragmento
                        if (totalUpdateListener != null) {
                            String total = CarrinhoDeCompras.getTotal();
                            totalUpdateListener.onTotalUpdated(total);
                        }
                    }
                }
            });
        }
    }

    @Override
    public ProdutoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_carrinho_lista, parent, false);
        return new ProdutoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProdutoViewHolder holder, int position) {
        if (CarrinhoDeCompras.produtos != null) {
            ItemCarrinho produto = CarrinhoDeCompras.produtos.get(position);
            holder.nomeProduto.setText(produto.getProduto().getNome());
            holder.descricaoProduto.setText(produto.getProduto().getDescricao());
            holder.precoProduto.setText("R$ " + produto.getPrecoUnitario());
            holder.imagem.setImageResource(produto.getProduto().getId());
            holder.quantidadeProduto.setText(String.valueOf(produto.getQuantidade()));
        }
    }

    @Override
    public int getItemCount() {
        return CarrinhoDeCompras.produtos != null ? CarrinhoDeCompras.produtos.size() : 0;
    }
}
