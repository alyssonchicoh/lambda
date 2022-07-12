package br.com.eoxygen.dto;

import br.com.eoxygen.enuns.TipoParametroEnum;
import br.com.eoxygen.enuns.UnidadeMedidaEnum;
import br.com.eoxygen.model.Usina;

public class ParametroDTO {

    private Long id;
    private String nome;
    private String label;
    private String unidadeMedida;
    private Double limiarSuperior;
    private Double limiarInferior;
    private Double porcentagemAproximacaoLimiarSuperior;
    private Double porcentagemAproximacaoLimiarInferior;
    private Long idUsina;

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

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Double getLimiarSuperior() {
        return limiarSuperior;
    }

    public void setLimiarSuperior(Double limiarSuperior) {
        this.limiarSuperior = limiarSuperior;
    }

    public Double getLimiarInferior() {
        return limiarInferior;
    }

    public void setLimiarInferior(Double limiarInferior) {
        this.limiarInferior = limiarInferior;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getPorcentagemAproximacaoLimiarSuperior() {
        return porcentagemAproximacaoLimiarSuperior;
    }

    public void setPorcentagemAproximacaoLimiarSuperior(Double porcentagemAproximacaoLimiarSuperior) {
        this.porcentagemAproximacaoLimiarSuperior = porcentagemAproximacaoLimiarSuperior;
    }

    public Double getPorcentagemAproximacaoLimiarInferior() {
        return porcentagemAproximacaoLimiarInferior;
    }

    public void setPorcentagemAproximacaoLimiarInferior(Double porcentagemAproximacaoLimiarInferior) {
        this.porcentagemAproximacaoLimiarInferior = porcentagemAproximacaoLimiarInferior;
    }

    public Long getIdUsina() {
        return idUsina;
    }

    public void setIdUsina(Long idUsina) {
        this.idUsina = idUsina;
    }
}
