package model;

import java.io.Serializable;

public class Cliente implements Serializable {
    private static int contador = 0; // Contador estático para gerar IDs únicos
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private Endereco endereco;

    // Construtor
    public Cliente(String nome, String email, String telefone, String senha, Endereco endereco) {
        this.id = ++contador; // Incrementa o contador e atribui ao id
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.endereco = endereco;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }
}
