package model;

public class Endereco {

    private int id;
    private String rua;
    private String numero;
    private String complemento;
    private String cep;

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }
}
