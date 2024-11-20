package model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemCarrinho  implements Serializable {
    private Produto produto;
    private int quantidade;
    private BigDecimal precoUnitario;

    public ItemCarrinho(Produto produto, int quantidade, BigDecimal precoUnitario) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public ItemCarrinho(){}


    public Double getSubTotal(){
        return getPrecoUnitario().doubleValue() * getQuantidade();
    }

    // Getters e Setters
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}
