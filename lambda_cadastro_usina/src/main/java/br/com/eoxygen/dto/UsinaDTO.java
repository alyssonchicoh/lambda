package br.com.eoxygen.dto;

public class UsinaDTO {

    private Long id;
    private String nome;
    private String numeroSerie;
    private String fabricante;
    private String dataConstrucao;
    private String dataCompra;
    private String idCLP;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getDataConstrucao() {
        return dataConstrucao;
    }

    public void setDataConstrucao(String dataConstrucao) {
        this.dataConstrucao = dataConstrucao;
    }

    public String getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(String dataCompra) {
        this.dataCompra = dataCompra;
    }

    public String getIdCLP() {
        return idCLP;
    }

    public void setIdCLP(String idCLP) {
        this.idCLP = idCLP;
    }
}
