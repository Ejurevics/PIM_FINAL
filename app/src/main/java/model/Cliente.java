package model;

import java.io.Serializable;

public class Cliente implements Serializable {
    private static int contador = 0;
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String rua;
    private int numero;

    public Cliente() {
        this.id = ++contador;
    }

    // Construtor atualizado
    public Cliente(String nome, String email, String telefone, String senha, String rua, int numero) {
        this.id = ++contador; // Garante que o ID seja gerado automaticamente
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.rua = rua;
        this.numero = numero;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", telefone='" + telefone + '\'' +
                ", rua='" + rua + '\'' +
                ", numero=" + numero +
                '}';
    }
}
