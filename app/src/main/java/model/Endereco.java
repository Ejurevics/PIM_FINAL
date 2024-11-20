package model;

import androidx.annotation.NonNull;

public class Endereco {

    private int id;
    private String rua;
    private String numero;
    private String complemento;
    private String cep;

    public Endereco(int id, String rua, String numero, String complemento, String cep) {
        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
    }

    public Endereco() {
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s - %s, %s", rua, numero, cep);
    }
}
