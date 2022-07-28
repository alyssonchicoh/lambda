package br.com.eoxygen.dto;

import br.com.eoxygen.model.Alarme;
import br.com.eoxygen.model.Hospital;
import br.com.eoxygen.model.Parametro;
import br.com.eoxygen.model.Usuario;

import java.util.Date;

/**
 * @author Alysson
 * @since 05/04/2022
 */
public class UsinaDTO extends DTO{

    private Long id;
    private String nome;
    private String numeroSerie;
    private String fabricante;
    private Date dataConstrucao;
    private Date dataCompra;
    private HospitalDTO hospital;
    private Long idAlocacao;
    private Long idUsinaBaseSinai;

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

    public Date getDataConstrucao() {
        return dataConstrucao;
    }

    public void setDataConstrucao(Date dataConstrucao) {
        this.dataConstrucao = dataConstrucao;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public HospitalDTO getHospital() {
        return hospital;
    }

    public void setHospital(HospitalDTO hospital) {
        this.hospital = hospital;
    }

    public Long getIdAlocacao() {
        return idAlocacao;
    }

    public void setIdAlocacao(Long idAlocacao) {
        this.idAlocacao = idAlocacao;
    }

    public Long getIdUsinaBaseSinai() {
        return idUsinaBaseSinai;
    }

    public void setIdUsinaBaseSinai(Long idUsinaBaseSinai) {
        this.idUsinaBaseSinai = idUsinaBaseSinai;
    }
}
