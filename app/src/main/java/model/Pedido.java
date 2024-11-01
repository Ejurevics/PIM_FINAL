package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;
    private List<ItemCarrinho> itens;
    private Endereco enderecoEntrega;
    private BigDecimal valorTotal;
    private BigDecimal frete;
    private LocalDateTime dataPedido;

    // Construtor
    public Pedido(int id, Cliente cliente, List<ItemCarrinho> itens, Endereco enderecoEntrega,
                  BigDecimal valorTotal, BigDecimal frete, LocalDateTime dataPedido) {
        this.id = id;
        this.cliente = cliente;
        this.itens = itens;
        this.enderecoEntrega = enderecoEntrega;
        this.valorTotal = valorTotal;
        this.frete = frete;
        this.dataPedido = dataPedido;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<ItemCarrinho> getItens() { return itens; }
    public void setItens(List<ItemCarrinho> itens) { this.itens = itens; }

    public Endereco getEnderecoEntrega() { return enderecoEntrega; }
    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public BigDecimal getFrete() { return frete; }
    public void setFrete(BigDecimal frete) { this.frete = frete; }

    public LocalDateTime getDataPedido() { return dataPedido; }
    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

}