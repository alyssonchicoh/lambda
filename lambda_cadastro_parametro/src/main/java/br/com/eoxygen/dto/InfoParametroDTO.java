package br.com.eoxygen.dto;

import br.com.eoxygen.enuns.TipoParametroEnum;
import br.com.eoxygen.enuns.UnidadeMedidaEnum;

public class InfoParametroDTO {

    private String cnpjCliente;
    private Long id;
    private TipoParametroEnum nome;
    private UnidadeMedidaEnum unidadeMedida;
    private Double limiarSuperior;
    private Double limiarInferior;
    private Double porcentagemAproximacaoLimiarSuperior;
    private Double porcentagemAproximacaoLimiarInferior;
    private String label;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoParametroEnum getNome() {
        return nome;
    }

    public void setNome(TipoParametroEnum nome) {
        this.nome = nome;
    }

    public UnidadeMedidaEnum getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedidaEnum unidadeMedida) {
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCnpjCliente() {
        return cnpjCliente;
    }

    public void setCnpjCliente(String cnpjCliente) {
        this.cnpjCliente = cnpjCliente;
    }
}
