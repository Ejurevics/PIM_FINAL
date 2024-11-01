package model;

import java.math.BigDecimal;

public class Produto {
    private int id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int quantidadeEmEstoque;

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public int getQuantidadeEmEstoque() { return quantidadeEmEstoque; }
    public void setQuantidadeEmEstoque(int quantidadeEmEstoque) { this.quantidadeEmEstoque = quantidadeEmEstoque; }
}
