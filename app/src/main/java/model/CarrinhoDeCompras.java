package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoDeCompras {
    private List<ItemCarrinho> itens;
    private BigDecimal valorTotal;

    public CarrinhoDeCompras() {
        this.itens = new ArrayList<>();
        this.valorTotal = BigDecimal.ZERO;
    }

    // Método para adicionar um item ao carrinho
    public void adicionarItem(Produto produto, int quantidade) {
        for (ItemCarrinho item : itens) {
            if (item.getProduto().getId() == produto.getId()) {
                item.setQuantidade(item.getQuantidade() + quantidade);
                atualizarValorTotal();
                return;
            }
        }
        // Se o item não está no carrinho, cria um novo
        ItemCarrinho novoItem = new ItemCarrinho();
        novoItem.setProduto(produto);
        novoItem.setQuantidade(quantidade);
        novoItem.setPrecoUnitario(produto.getPreco());
        itens.add(novoItem);
        atualizarValorTotal();
    }

    // Método para remover um item do carrinho
    public void removerItem(Produto produto) {
        itens.removeIf(item -> item.getProduto().getId() == produto.getId());
        atualizarValorTotal();
    }

    // Método para calcular e atualizar o valor total
    private void atualizarValorTotal() {
        valorTotal = itens.stream()
                .map(item -> item.getPrecoUnitario().multiply(new BigDecimal(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Getter para o valor total
    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    // Getter para os itens
    public List<ItemCarrinho> getItens() {
        return itens;
    }

    // Método para limpar o carrinho
    public void limparCarrinho() {
        itens.clear();
        valorTotal = BigDecimal.ZERO;
    }
}
