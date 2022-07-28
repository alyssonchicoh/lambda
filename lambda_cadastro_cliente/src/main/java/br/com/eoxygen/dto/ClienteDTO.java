package br.com.eoxygen.dto;

public class ClienteDTO {

    private String cnpj;
    private String nome;
    private String endereco;
    private String idCidade;
    private String email;

    public boolean existeCnpj(){
        return cnpj != null && !cnpj.equals("");
    }

    public boolean existeNome(){
        return nome != null && !nome.equals("");
    }

    public boolean existeEndereco(){
        return endereco != null && !endereco.equals("");
    }

    public boolean existeIdCidade(){
        return idCidade != null && !idCidade.equals("");
    }

    public boolean existeEmail(){
        return email != null && !email.equals("");
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(String idCidade) {
        this.idCidade = idCidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
